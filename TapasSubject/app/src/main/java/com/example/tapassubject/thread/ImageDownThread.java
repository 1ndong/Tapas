package com.example.tapassubject.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tapassubject.data.ThumbInfo;
import com.example.tapassubject.listener.IImageDownLoadListener;
import com.example.tapassubject.retrofit.RetrofitConnector;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDownThread extends Thread{

    private String url;
    private IImageDownLoadListener listener;
    private int pos;

    public ImageDownThread(int pos , String url , IImageDownLoadListener listener)
    {
        this.pos = pos;
        this.url = url;
        this.listener = listener;
    }

    @Override
    public void run() {
        Call<ResponseBody> imagedownload = RetrofitConnector.getApiService().downloadImage(url);
        imagedownload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                listener.OnFinishImageDownLoad(bitmap,pos);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
