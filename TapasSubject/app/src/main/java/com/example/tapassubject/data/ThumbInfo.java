package com.example.tapassubject.data;

import android.graphics.Bitmap;

public class ThumbInfo {

    private boolean isBookcover = false;
    private Bitmap bitmap;
    private int width;
    private int height;
    private String url;

    public ThumbInfo(String url , int w , int h , boolean isBookcover)
    {
        this.url = url;
        width = w;
        height = h;
        this.isBookcover = isBookcover;
    }

    public boolean isBookcover() {
        return isBookcover;
    }

    public String getURL()
    {
        return url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
