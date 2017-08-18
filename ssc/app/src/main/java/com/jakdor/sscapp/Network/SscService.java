package com.jakdor.sscapp.Network;

import com.jakdor.sscapp.Model.AppData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SscService {

    @GET("app_data")
    Call<AppData> getAppData();

    @GET("last_update")
    Call<String> getLastUpdateId();
}
