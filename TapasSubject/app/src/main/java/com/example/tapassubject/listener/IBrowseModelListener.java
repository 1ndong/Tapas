package com.example.tapassubject.listener;

import com.example.tapassubject.data.ThumbInfo;
import com.example.tapassubject.model.PaginationModel;

public interface IBrowseModelListener {
    public void OnBeforeStartTask();
    public void OnSetPaginationInfo(PaginationModel pm);
    public void OnAddImageInfo(ThumbInfo url);
    public void OnFinishBrowseModelRequest();
}
