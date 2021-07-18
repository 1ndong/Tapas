package com.example.tapassubject.listener;

public interface IBrowseModelListener {
    public void OnBeforeStartTask();
    public void OnAddImageURL(String url);
    public void OnFinishBrowseModelRequest();
}
