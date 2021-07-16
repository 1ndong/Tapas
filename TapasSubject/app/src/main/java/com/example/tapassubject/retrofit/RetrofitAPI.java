package com.example.tapassubject.retrofit;

import com.example.tapassubject.model.BrowseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("browse/fresh")
    Call<BrowseModel> requestBrowse(@Query("series_type") String comics , @Query("page") int page);
}
