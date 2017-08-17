package com.jakdor.sscapp.Network;

import android.util.Log;

import com.jakdor.sscapp.Model.AppData;
import com.jakdor.sscapp.Model.Host;
import com.jakdor.sscapp.Model.Sponsor;
import com.jakdor.sscapp.Model.Timetable;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {

    private final String CLASS_TAG = "NetworkManager";

    private SscService sscService;

    public NetworkManager(){
        sscService = SscApi.getClient().create(SscService.class);
    }

    //retrofit call to api
    private Call<AppData> callAppData(){
        return sscService.getAppData();
    }

    //parse tables from appData
    private List<Timetable> fetchTimetables(Response<AppData> response){
        AppData appData = response.body();
        return appData.getTimetables();
    }

    private List<Host> fetchHosts(Response<AppData> response){
        AppData appData = response.body();
        return appData.getHosts();
    }

    private List<Sponsor> fetchSponsors(Response<AppData> response){
        AppData appData = response.body();
        return appData.getSponsors();
    }

    public void loadAppData(){
        callAppData().enqueue(new Callback<AppData>() {
            @Override
            public void onResponse(Call<AppData> call, Response<AppData> response) {

                boolean stopLoad = false;

                List<Timetable> timetables = fetchTimetables(response);
                List<Host> hosts = fetchHosts(response);
                List<Sponsor> sponsors = fetchSponsors(response);

                Log.i(CLASS_TAG, "Api Test: "  + timetables.get(0).getName());

                try{ //delete entries, reset id autoincrement
                    Timetable.deleteAll(Timetable.class);
                    Host.deleteAll(Host.class);
                    Sponsor.deleteAll(Sponsor.class);
                    Timetable.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'TIMETABLE'");
                    Host.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'HOST'");
                    Sponsor.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'SPONSOR'");
                }
                catch (Exception e){
                    Log.e(CLASS_TAG, "local db critical error: " + e.getMessage());
                    stopLoad = true;
                }

                if(!stopLoad) {

                    for (Timetable timetable : timetables) {
                        timetable.setId(null);
                        timetable.save();
                    }
                    for(Host host : hosts){
                        host.setId(null);
                        host.save();
                    }
                    for(Sponsor sponsor : sponsors){
                        sponsor.setId(null);
                        sponsor.save();
                    }
                }

                Host testHost = Host.findById(Host.class, (long)1);
                Log.i(CLASS_TAG, "db Test: " + Long.toString(testHost.getId()) +
                        ". " + testHost.getName() + " | " + testHost.getInfo());
            }

            @Override
            public void onFailure(Call<AppData> call, Throwable t) {
                Log.e(CLASS_TAG, "connection problem: " + t.getMessage());
            }
        });
    }

}
