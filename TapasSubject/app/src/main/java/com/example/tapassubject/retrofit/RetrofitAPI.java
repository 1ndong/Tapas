package com.example.tapassubject.retrofit;

import com.example.tapassubject.model.BrowseModel;
import com.example.tapassubject.model.EpisodeModel;
import com.example.tapassubject.model.SeriesModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET("browse/fresh")
    Call<BrowseModel> requestBrowse(@Query("series_type") String comics , @Query("page") int page);

    @GET("series/{series_id}")
    Call<SeriesModel> requestSeries(@Path("series_id") int series_id);

    @GET("series/{series_id}/episodes")
    Call<JsonArray> requestEpisode(@Path("series_id") int series_id);

    @GET
    Call<ResponseBody> downloadImage(@Url String fileurl);
}
