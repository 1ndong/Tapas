package com.example.tapassubject.model;

import java.util.ArrayList;
import java.util.List;

public class BrowseModel {
    private PaginationModel pagination;
    private List<SeriesModel> Series = new ArrayList<>();

    public PaginationModel getPagination() {
        return pagination;
    }

    public void setPagination(PaginationModel pagination) {
        this.pagination = pagination;
    }

    public List<SeriesModel> getSeries() {
        return Series;
    }

    public void addSeries(SeriesModel series) {
        Series.add(series);
    }
}
