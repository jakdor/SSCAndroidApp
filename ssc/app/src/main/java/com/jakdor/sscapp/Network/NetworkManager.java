package com.jakdor.sscapp.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jakdor.sscapp.Model.AppData;
import com.jakdor.sscapp.Model.Host;
import com.jakdor.sscapp.Model.Sponsor;
import com.jakdor.sscapp.Model.Timetable;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager{

    private final String CLASS_TAG = "NetworkManager";

    private SscService sscService;
    private Boolean dbReady = false;
    private int apiLastUpdateId;
    private int localLastUpdateId;

    private static NetworkManager instance = null;

    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }

        return instance;
    }

    private NetworkManager(){
        sscService = SscApi.getClient().create(SscService.class);
    }

    public void checkForUpdate(final Context context){
        SharedPreferences settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        localLastUpdateId = settings.getInt("dbLastUpdate", 0);

        Log.i(CLASS_TAG, "Local lastUpdateID pre update: " + Integer.toString(localLastUpdateId));

        checkLastUpdateId(context);
    }

    //retrofit calls to api
    private Call<AppData> callAppData() {
        return sscService.getAppData();
    }

    private Call<String> callLastUpdate() {
        return sscService.getLastUpdateId();
    }

    //parse tables from appData
    private List<Timetable> fetchTimetables(Response<AppData> response) {
        AppData appData = response.body();
        return appData.getTimetables();
    }

    private List<Host> fetchHosts(Response<AppData> response) {
        AppData appData = response.body();
        return appData.getHosts();
    }

    private List<Sponsor> fetchSponsors(Response<AppData> response) {
        AppData appData = response.body();
        return appData.getSponsors();
    }

    private void updateCheck(Context context) {
        if (apiLastUpdateId != localLastUpdateId) {
            loadAppData(context);
        } else {
            Log.i(CLASS_TAG, "Local db up-to-date");
            dbReady = true;
        }
    }

    private void postUpdate(Context context) {
        localLastUpdateId = apiLastUpdateId;

        Log.i(CLASS_TAG, "Local lastUpdateID post update: " + Integer.toString(localLastUpdateId));

        SharedPreferences settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("dbLastUpdate", localLastUpdateId);
        editor.apply();

        dbReady = true;
    }

    private void checkLastUpdateId(final Context context) {
        callLastUpdate().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(CLASS_TAG, "Last update id: " + response.body());
                apiLastUpdateId = Integer.parseInt(response.body());
                updateCheck(context);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(CLASS_TAG, "connection problem: " + t.getMessage());
                dbReady = true; //todo implement failed to get init data
            }
        });
    }

    private void loadAppData(final Context context) {
        callAppData().enqueue(new Callback<AppData>() {
            @Override
            public void onResponse(Call<AppData> call, Response<AppData> response) {

                List<Timetable> timetables = fetchTimetables(response);
                List<Host> hosts = fetchHosts(response);
                List<Sponsor> sponsors = fetchSponsors(response);

                Log.i(CLASS_TAG, "Api Test: " + timetables.get(0).getName());

                new Thread(() -> {
                    boolean stopLoad = false;

                    try { //delete entries, reset id autoincrement
                        Timetable.deleteAll(Timetable.class);
                        Host.deleteAll(Host.class);
                        Sponsor.deleteAll(Sponsor.class);
                        Timetable.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'TIMETABLE'");
                        Host.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'HOST'");
                        Sponsor.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'SPONSOR'");
                    } catch (Exception e) {
                        Log.e(CLASS_TAG, "local db critical error: " + e.getMessage());
                        stopLoad = true;
                    }

                    if (!stopLoad) {

                        for (Timetable timetable : timetables) {
                            timetable.setId(null);
                            timetable.save();
                        }
                        for (Host host : hosts) {
                            host.setId(null);
                            host.save();
                        }
                        for (Sponsor sponsor : sponsors) {
                            sponsor.setId(null);
                            sponsor.save();
                        }
                    }

                    postUpdate(context);
                }).start();
            }

            @Override
            public void onFailure(Call<AppData> call, Throwable t) {
                Log.e(CLASS_TAG, "connection problem: " + t.getMessage());
                dbReady = true;
            }
        });
    }


    public Boolean isDbReady() {
        return dbReady;
    }
}
