package com.example.tapassubject.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.tapassubject.Const;

public class RetrofitConnector {
     public static RetrofitAPI getApiService()
     {
         return getInstance().create(RetrofitAPI.class);
     }

     private static Retrofit getInstance()
     {
         Gson gson = new GsonBuilder().setLenient().create();
         return new Retrofit.Builder().baseUrl(Const.BASEURL)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build();
     }
}
