package com.example.tapassubject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.tapassubject.listener.IEpisodeThreadListener;
import com.example.tapassubject.listener.IImageDownLoadListener;
import com.example.tapassubject.listener.ISeriesThreadListener;
import com.example.tapassubject.model.EpisodeModel;
import com.example.tapassubject.model.SeriesModel;
import com.example.tapassubject.retrofit.RetrofitConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity
        implements IEpisodeThreadListener , ISeriesThreadListener , IImageDownLoadListener {
    SeriesModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        model = (SeriesModel)intent.getSerializableExtra("model");
    }

    @Override
    public void OnFinishEpisodeThread() {

    }

    @Override
    public void OnFinishImageDownLoad(Bitmap result, int pos) {

    }

    @Override
    public void OnFinishSeriesThread() {

    }

    private class SeriesThread extends Thread
    {
        private ISeriesThreadListener listener;

        public SeriesThread(ISeriesThreadListener listener)
        {
            this.listener = listener;
        }

        @Override
        public void run() {
            Call<SeriesModel> seriesModelCall = RetrofitConnector.getApiService().requestSeries(model.getId());
            seriesModelCall.enqueue(new Callback<SeriesModel>() {
                @Override
                public void onResponse(Call<SeriesModel> call, Response<SeriesModel> response) {
                    SeriesModel getModel = response.body();

                    listener.OnFinishSeriesThread();
                }

                @Override
                public void onFailure(Call<SeriesModel> call, Throwable t) {

                }
            });
        }
    }

    private class EpisodeThread extends Thread
    {
        private IEpisodeThreadListener listener;

        public EpisodeThread(IEpisodeThreadListener listener)
        {
            this.listener = listener;
        }

        @Override
        public void run() {
            Call<JsonArray> episodeModelCall = RetrofitConnector.getApiService().requestEpisode(model.getId());
            episodeModelCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    JsonArray array = response.body();
                    Gson gson = new Gson();
                    ArrayList<EpisodeModel> result
                            = gson.fromJson(array.toString() , new TypeToken<ArrayList<EpisodeModel>>(){}.getType());

                    listener.OnFinishEpisodeThread();
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {

                }
            });
        }
    }
}