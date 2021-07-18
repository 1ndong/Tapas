package com.example.tapassubject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BrowseModel implements Serializable {
    private PaginationModel pagination;
    private List<SeriesModel> series = new ArrayList<>();

    public PaginationModel getPagination() {
        return pagination;
    }

    public void setPagination(PaginationModel pagination) {
        this.pagination = pagination;
    }

    public List<SeriesModel> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesModel> series) {
        this.series = series;
    }
}
