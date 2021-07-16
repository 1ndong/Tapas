package com.example.tapassubject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tapassubject.model.BrowseModel;
import com.example.tapassubject.retrofit.RetrofitConnector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<BrowseModel> browseModelCall = RetrofitConnector.getApiService().requestBrowse("COMICS" , 1);

        browseModelCall.enqueue(new Callback<BrowseModel>() {
            @Override
            public void onResponse(Call<BrowseModel> call, Response<BrowseModel> response) {
                BrowseModel str = response.body();
            }

            @Override
            public void onFailure(Call<BrowseModel> call, Throwable t) {

            }
        });
    }
}