package com.example.tapassubject.listener;

import com.example.tapassubject.data.ThumbInfo;

public interface IBrowseModelListener {
    public void OnBeforeStartTask();
    public void OnAddImageInfo(ThumbInfo url);
    public void OnFinishBrowseModelRequest();
}
