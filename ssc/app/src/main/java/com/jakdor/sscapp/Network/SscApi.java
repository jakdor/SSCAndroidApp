package com.jakdor.sscapp.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SscApi {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(4, TimeUnit.SECONDS)
                    .readTimeout(4, TimeUnit.SECONDS)
                    .writeTimeout(4, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.1.185:8000/api/")
                    .client(okHttpClient)
                    .build();
        }
        //192.168.43.101:8000
        return retrofit;
    }
}
