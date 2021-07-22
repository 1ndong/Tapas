package com.example.tapassubject.listener;

import android.graphics.Bitmap;

public interface IImageDownloadThreadListener {
    void OnFinishImageDownLoad(Bitmap result,int pos);
}
