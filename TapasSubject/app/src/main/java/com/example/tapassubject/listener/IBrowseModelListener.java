package com.example.tapassubject.listener;

import com.example.tapassubject.MainActivity;
import com.example.tapassubject.data.ThumbInfo;
import com.example.tapassubject.model.PaginationModel;
import com.example.tapassubject.model.SeriesModel;

public interface IBrowseModelListener {
    public void OnBeforeStartTask();
    public void OnSetPaginationInfo(PaginationModel pm);
    public void OnAddItemInfo(ThumbInfo url , SeriesModel model);
    public void OnFinishBrowseModelRequest(MainActivity.eActionType actionType);
}
