package com.example.tapassubject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tapassubject.list.CreatorCustomAdapter;
import com.example.tapassubject.list.EpisodeCustomAdapter;
import com.example.tapassubject.listener.IEpisodeThreadListener;
import com.example.tapassubject.listener.IImageDownLoadListener;
import com.example.tapassubject.listener.ISeriesThreadListener;
import com.example.tapassubject.model.CreatorModel;
import com.example.tapassubject.model.EpisodeModel;
import com.example.tapassubject.model.SeriesModel;
import com.example.tapassubject.retrofit.RetrofitConnector;
import com.example.tapassubject.thread.ImageDownThread;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity
        implements IEpisodeThreadListener , ISeriesThreadListener , IImageDownLoadListener {

    final int BOOKCOVER = 0;
    final int THUMB = 1;

    private ImageView imageView;
    private TextView titleView;

    private RecyclerView creatorRecyclerView;
    private CreatorCustomAdapter creatorCustomAdapter;

    private RecyclerView episodeRecyclerView;
    private EpisodeCustomAdapter episodeCustomAdapter;

    private List<EpisodeModel> episodeModelItemList = new ArrayList<>();
    private List<CreatorModel> creatormodelItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.detailimageview);
        titleView= findViewById(R.id.detailtitle);
        creatorRecyclerView = findViewById(R.id.creatorrecyclerview);
        creatorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        creatorCustomAdapter = new CreatorCustomAdapter(creatormodelItemList);
        creatorRecyclerView.setAdapter(creatorCustomAdapter);

        episodeRecyclerView = findViewById(R.id.episoderecyclerview);
        episodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        episodeCustomAdapter = new EpisodeCustomAdapter(episodeModelItemList);
        episodeRecyclerView.setAdapter(episodeCustomAdapter);

        Intent intent = getIntent();
        SeriesModel model = (SeriesModel)intent.getSerializableExtra("model");

        new SeriesThread(this , model.getId()).start();
    }

    @Override
    public void OnFinishEpisodeThread(List<EpisodeModel> models) {
        //에피소드 완료된걸로 recyclerview 만들어줘야됨
        episodeModelItemList.clear();

        for(EpisodeModel model : models)
        {
            episodeModelItemList.add(model);
        }

        episodeCustomAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnFinishImageDownLoad(Bitmap result, int pos) {
        int width = 300;
        int height = 300;

        if(pos == BOOKCOVER)
        {
            height = (int)(width * 1.5);
        }

        imageView.requestLayout();
        imageView.getLayoutParams().width = width;
        imageView.getLayoutParams().height = height;
        imageView.setImageBitmap(result);
    }

    @Override
    public void OnFinishSeriesThread(SeriesModel model) {
        titleView.setText(model.getTitle());
        List<CreatorModel> creatorModels = model.getCreators();
        for(CreatorModel creatormodel : creatorModels)
        {
            creatormodelItemList.add(creatormodel);
        }

        creatorCustomAdapter.notifyDataSetChanged();

        int bitmapType = BOOKCOVER;
        String url = model.getBook_cover_url();
        if(url == null)
        {
            url = model.getThumb().getFile_url();
            bitmapType = THUMB;
        }

        new ImageDownThread(bitmapType , url , this).start();
        new EpisodeThread(this , model.getId()).start();
    }

    private class SeriesThread extends Thread
    {
        private ISeriesThreadListener listener;
        private int id;

        public SeriesThread(ISeriesThreadListener listener , int id)
        {
            this.listener = listener;
            this.id = id;
        }

        @Override
        public void run() {
            Call<SeriesModel> seriesModelCall = RetrofitConnector.getApiService().requestSeries(id);
            seriesModelCall.enqueue(new Callback<SeriesModel>() {
                @Override
                public void onResponse(Call<SeriesModel> call, Response<SeriesModel> response) {
                    listener.OnFinishSeriesThread(response.body());
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
        private int id;

        public EpisodeThread(IEpisodeThreadListener listener , int id)
        {
            this.listener = listener;
            this.id = id;
        }

        @Override
        public void run() {
            Call<JsonArray> episodeModelCall = RetrofitConnector.getApiService().requestEpisode(id);
            episodeModelCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    JsonArray array = response.body();
                    Gson gson = new Gson();
                    ArrayList<EpisodeModel> result
                            = gson.fromJson(array.toString() , new TypeToken<ArrayList<EpisodeModel>>(){}.getType());

                    listener.OnFinishEpisodeThread(result);
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {

                }
            });
        }
    }
}