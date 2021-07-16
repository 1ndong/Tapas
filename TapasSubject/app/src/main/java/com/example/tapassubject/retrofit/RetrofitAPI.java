package com.example.tapassubject.retrofit;

import com.example.tapassubject.model.BrowseModel;
import com.example.tapassubject.model.EpisodeModel;
import com.example.tapassubject.model.SeriesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("browse/fresh")
    Call<BrowseModel> requestBrowse(@Query("series_type") String comics , @Query("page") int page);

    @GET("series/{series_id}")
    Call<SeriesModel> requestSeries(@Path("series_id") int series_id);

    @GET("series/{series_id}/episodes")
    Call<EpisodeModel> requestEpisode(@Path("series_id") int series_id);
}
