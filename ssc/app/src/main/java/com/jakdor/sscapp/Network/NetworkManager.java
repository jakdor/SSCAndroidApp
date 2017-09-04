package com.jakdor.sscapp.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private int dbReady = 0;
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

    private boolean checkNetworkStatus(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            Log.e(CLASS_TAG, "Internet status: no service!");
            return false;
        }
        else {
            Log.i(CLASS_TAG, "Internet status: OK");
            return true;
        }
    }

    public void checkForUpdate(final Context context){
        dbReady = 0; //block db access

        SharedPreferences settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        localLastUpdateId = settings.getInt("dbLastUpdate", 0);
        Log.i(CLASS_TAG, "Local lastUpdateID pre update: " + Integer.toString(localLastUpdateId));

        if(checkNetworkStatus(context)) {
            checkLastUpdateId(context);
        }
        else {
            if(localLastUpdateId == 0){
                dbReady = 3;
            }
            else {
                dbReady = 2;
            }
        }
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
            dbReady = 1;
        }
    }

    private void postUpdate(Context context) {
        localLastUpdateId = apiLastUpdateId;

        Log.i(CLASS_TAG, "Local lastUpdateID post update: " + Integer.toString(localLastUpdateId));

        SharedPreferences settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("dbLastUpdate", localLastUpdateId);
        editor.apply();

        dbReady = 1;
    }

    private void connectionProblem(Throwable throwable){
        Log.e(CLASS_TAG, "connection problem: " + throwable.getMessage());
        if(localLastUpdateId == 0){
            dbReady = 3;
        }
        else {
            dbReady = 2;
        }
    }

    private void checkLastUpdateId(final Context context) {
        callLastUpdate().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() == null){
                    connectionProblem(new Throwable("Server returned null"));
                    return;
                }
                Log.i(CLASS_TAG, "Last db update ID: " + response.body());
                apiLastUpdateId = Integer.parseInt(response.body());
                updateCheck(context);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                connectionProblem(t);
            }
        });
    }

    private void loadAppData(final Context context) {
        callAppData().enqueue(new Callback<AppData>() {
            @Override
            public void onResponse(Call<AppData> call, Response<AppData> response) {
                if(response.body() == null){
                    connectionProblem(new Throwable("Server returned null"));
                    return;
                }

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
                connectionProblem(t);
            }
        });
    }

    public int isDbReady() {
        return dbReady;
    }

    public void networkProblemInfoDisplayed(){
        dbReady = 1;
    }
}
