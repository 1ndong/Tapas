package com.example.tapassubject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tapassubject.model.BrowseModel;
import com.example.tapassubject.model.EpisodeModel;
import com.example.tapassubject.model.SeriesModel;
import com.example.tapassubject.retrofit.RetrofitConnector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    SeriesModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        model = (SeriesModel)intent.getSerializableExtra("model");
/*
        Call<SeriesModel> seriesModelCall = RetrofitConnector.getApiService().requestSeries(model.getId());
        seriesModelCall.enqueue(new Callback<SeriesModel>() {
            @Override
            public void onResponse(Call<SeriesModel> call, Response<SeriesModel> response) {
                SeriesModel getModel = response.body();
            }

            @Override
            public void onFailure(Call<SeriesModel> call, Throwable t) {

            }
        });
*/
        Call<EpisodeModel> episodeModelCall = RetrofitConnector.getApiService().requestEpisode(model.getId());
        episodeModelCall.enqueue(new Callback<EpisodeModel>() {
            @Override
            public void onResponse(Call<EpisodeModel> call, Response<EpisodeModel> response) {
                EpisodeModel model = response.body();
            }

            @Override
            public void onFailure(Call<EpisodeModel> call, Throwable t) {

            }
        });
    }
}