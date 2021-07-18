package com.example.tapassubject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.example.tapassubject.list.CustomAdapter;
import com.example.tapassubject.listener.IBrowseModelListener;
import com.example.tapassubject.model.BrowseModel;
import com.example.tapassubject.model.PaginationModel;
import com.example.tapassubject.model.SeriesModel;
import com.example.tapassubject.retrofit.RetrofitConnector;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IBrowseModelListener {
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private Handler mainHandler;
    private Button prevBtn;
    private Button nextBtn;
    public List<String> todoBitmapList = new ArrayList<>();
    private List<Bitmap> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler();

        customAdapter = new CustomAdapter(itemList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);

        prevBtn = findViewById(R.id.button);
        nextBtn = findViewById(R.id.button2);

        new BrowseThread(this , new BrowseInfo("COMICS",1),mainHandler).start();
    }

    @Override
    public void OnBeforeStartTask() {
        todoBitmapList.clear();
    }

    @Override
    public void OnAddImageURL(String url) {
        todoBitmapList.add(url);
    }

    @Override
    public void OnFinishBrowseModelRequest() {
        for(String url : todoBitmapList)
        {
            Call<ResponseBody> imagedownload = RetrofitConnector.getApiService().downloadImage(url);
            imagedownload.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    InputStream is = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    itemList.add(bitmap);

                    customAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    private class BrowseInfo
    {
        public String first;
        public int second;

        public BrowseInfo(String first , int second)
        {
            this.first = first;
            this.second = second;
        }
    }

    private class BrowseThread extends Thread {

        private BrowseInfo info;
        private Handler mainHandler;
        private IBrowseModelListener listener;

        public BrowseThread(IBrowseModelListener listener , BrowseInfo info , Handler handler)
        {
            this.listener = listener;
            this.info = info;
            mainHandler = handler;
        }

        @Override
        public void run() {
            Call<BrowseModel> browseModelCall = RetrofitConnector.getApiService().requestBrowse(info.first , info.second);

            browseModelCall.enqueue(new Callback<BrowseModel>() {
                @Override
                public void onResponse(Call<BrowseModel> call, Response<BrowseModel> response) {
                    BrowseModel browseModel = response.body();

                    PaginationModel pm = browseModel.getPagination();
                    List<SeriesModel> list = browseModel.getSeries();//이걸로 recyclerview만들업주면 되고
                    for(SeriesModel model : list)
                    {
                        String imgUrl = model.getBook_cover_url();
                        if(imgUrl == null)
                            imgUrl = model.getThumb().getFile_url();

                        listener.OnAddImageURL(imgUrl);
                    }

                    listener.OnFinishBrowseModelRequest();
                }

                @Override
                public void onFailure(Call<BrowseModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, Const.MSG_FAIL_INIT, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}