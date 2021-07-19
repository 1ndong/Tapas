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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tapassubject.data.ItemInfo;
import com.example.tapassubject.data.ThumbInfo;
import com.example.tapassubject.list.CustomAdapter;
import com.example.tapassubject.listener.IBrowseModelListener;
import com.example.tapassubject.listener.IImageDownLoadListener;
import com.example.tapassubject.model.BrowseModel;
import com.example.tapassubject.model.PaginationModel;
import com.example.tapassubject.model.SeriesModel;
import com.example.tapassubject.retrofit.RetrofitConnector;
import com.example.tapassubject.thread.ImageDownThread;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IBrowseModelListener , IImageDownLoadListener {
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private List<ItemInfo> itemList = new ArrayList<>();
    private TextView statusTextView;

    private ProgressBar refreshBar;
    private ProgressBar loadBar;

    private int currentPage = 1;
    private PaginationModel curPaginationModel;

    private BrowseThread browseThread = null;

    private boolean isLoadingMoreData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customAdapter = new CustomAdapter(itemList);
        customAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(getApplicationContext() , DetailActivity.class);

                intent.putExtra("model" , itemList.get(pos).getSeriesModel());

                startActivity(intent);
            }
        });

        statusTextView = findViewById(R.id.statusTextView);

        refreshBar = findViewById(R.id.refreshBar);
        loadBar = findViewById(R.id.loadBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                
                int lastvisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount()-1;

                if(lastvisibleItemPosition == itemTotalCount)
                {//끝에 도달했으면
                    if(!isLoadingMoreData)
                    {
                        makeBrowseThread(curPaginationModel.getPage());
                        isLoadingMoreData = true;
                    }
                }
            }
        });

        makeBrowseThread(1);
    }

    private void makeBrowseThread(int page)
    {
        loadBar.setVisibility(View.VISIBLE);
        new BrowseThread(this , new BrowseInfo("COMICS",page)).start();
    }

    @Override
    public void OnBeforeStartTask() {
        statusTextView.setText("start getBrowseModel");
    }

    @Override
    public void OnSetPaginationInfo(PaginationModel pm) {
        curPaginationModel = pm;
    }

    @Override
    public void OnAddItemInfo(ThumbInfo info , SeriesModel model) {
        itemList.add(new ItemInfo(info,model));
    }

    @Override
    public void OnFinishBrowseModelRequest() {
        customAdapter.notifyDataSetChanged();
        statusTextView.setText("finish getBrowseModel");
        isLoadingMoreData = false;
        loadBar.setVisibility(View.GONE);

        for(int i = 0 ; i < itemList.size() ; ++i)
        {
            ThumbInfo info = itemList.get(i).getThumbInfo();
            new ImageDownThread(i , info.getURL(),this).start();
        }
    }

    @Override
    public void OnFinishImageDownLoad(Bitmap result, int pos) {
        ItemInfo info = itemList.get(pos);
        info.getThumbInfo().setBitmap(result);
        customAdapter.notifyDataSetChanged();
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

                        listener.OnAddItemInfo(
                                new ThumbInfo(imgUrl
                                ,model.getThumb().getWidth()
                                ,model.getThumb().getHeight()
                                ,isBookcover
                                ,BitmapFactory.decodeResource(getResources(),R.drawable.loading))
                                ,model);
                    }

                    listener.OnFinishBrowseModelRequest();
                }

                @Override
                public void onFailure(Call<BrowseModel> call, Throwable t) {

                }
            });
        }
    }
}