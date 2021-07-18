package com.example.tapassubject.data;

import com.example.tapassubject.model.SeriesModel;

public class ItemInfo {
    private ThumbInfo thumbInfo;
    private SeriesModel seriesModel;

    public ItemInfo(ThumbInfo thumbInfo , SeriesModel seriesModel)
    {
        this.thumbInfo = thumbInfo;
        this.seriesModel = seriesModel;
    }

    public ThumbInfo getThumbInfo() {
        return thumbInfo;
    }

    public SeriesModel getSeriesModel() {
        return seriesModel;
    }
}
