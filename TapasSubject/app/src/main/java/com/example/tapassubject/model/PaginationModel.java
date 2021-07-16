package com.example.tapassubject.model;

public class PaginationModel {
    private int page;
    private boolean has_next;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }
}
