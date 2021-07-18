package com.example.tapassubject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tapassubject.data.ThumbInfo;
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
    private List<ThumbInfo> itemList = new ArrayList<>();
    private TextView statusTextView;

    private TextView curPageTextView;
    private Button prevBtn;
    private Button nextBtn;

    private int currentPage = 1;
    private PaginationModel curPaginationModel;

    private BrowseThread browseThread = null;

    private final int DETAIL_ACTIVITY = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customAdapter = new CustomAdapter(itemList);
        customAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(getApplicationContext() , DetailActivity.class);
                startActivityForResult(intent,DETAIL_ACTIVITY);
            }
        });

        statusTextView = findViewById(R.id.statusTextView);
        curPageTextView = findViewById(R.id.currentPageView);
        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage > 1)
                {
                    --currentPage;
                    makeBrowseThread();
                }
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curPaginationModel.isHas_next())
                {
                    currentPage = curPaginationModel.getPage();
                    makeBrowseThread();
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);

        makeBrowseThread();
    }

    private void makeBrowseThread()
    {
        setPrevBtnEnable(false);
        setNextBtnEnable(false);
        new BrowseThread(this , new BrowseInfo("COMICS",currentPage)).start();
    }

    private void setPrevBtnEnable(boolean enable)
    {
        if(curPaginationModel != null && currentPage == 1)
            prevBtn.setEnabled(false);
        else
            prevBtn.setEnabled(enable);
    }

    private void setNextBtnEnable(boolean enable)
    {
        if(curPaginationModel != null && !curPaginationModel.isHas_next())
            nextBtn.setEnabled(false);
        else
            nextBtn.setEnabled(enable);
    }

    @Override
    public void OnBeforeStartTask() {
        itemList.clear();
        statusTextView.setText("start getBrowseModel");
    }

    @Override
    public void OnSetPaginationInfo(PaginationModel pm) {
        curPaginationModel = pm;
    }

    @Override
    public void OnAddImageInfo(ThumbInfo info) {
        itemList.add(info);
    }

    @Override
    public void OnFinishBrowseModelRequest() {
        for(int i = 0 ; i < itemList.size() ; ++i)
        {
            ThumbInfo info = itemList.get(i);

            Call<ResponseBody> imagedownload = RetrofitConnector.getApiService().downloadImage(info.getURL());
            imagedownload.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    InputStream is = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    info.setBitmap(bitmap);

                    customAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        statusTextView.setText("finish getBrowseModel");
        curPageTextView.setText(""+currentPage);
        setPrevBtnEnable(true);
        setNextBtnEnable(true);
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
        private IBrowseModelListener listener;

        public BrowseThread(IBrowseModelListener listener , BrowseInfo info)
        {
            this.listener = listener;
            this.info = info;
        }

        @Override
        public void run() {
            listener.OnBeforeStartTask();

            Call<BrowseModel> browseModelCall = RetrofitConnector.getApiService().requestBrowse(info.first , info.second);

            browseModelCall.enqueue(new Callback<BrowseModel>() {
                @Override
                public void onResponse(Call<BrowseModel> call, Response<BrowseModel> response) {
                    BrowseModel browseModel = response.body();

                    listener.OnSetPaginationInfo(browseModel.getPagination());
                    List<SeriesModel> list = browseModel.getSeries();
                    for(SeriesModel model : list)
                    {
                        String imgUrl = model.getBook_cover_url();
                        boolean isBookcover = true;
                        if(imgUrl == null)
                        {
                            imgUrl = model.getThumb().getFile_url();
                            isBookcover = false;
                        }

                        listener.OnAddImageInfo(
                                new ThumbInfo(imgUrl
                                        ,model.getThumb().getWidth()
                                        ,model.getThumb().getHeight()
                                        ,isBookcover));
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