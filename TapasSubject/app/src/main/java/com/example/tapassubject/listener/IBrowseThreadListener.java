package com.example.tapassubject.listener;

import com.example.tapassubject.MainActivity;
import com.example.tapassubject.data.ThumbInfo;
import com.example.tapassubject.model.PaginationModel;
import com.example.tapassubject.model.SeriesModel;

public interface IBrowseThreadListener {
    void OnBeforeStartTask();
    void OnSetPaginationInfo(PaginationModel pm);
    void OnAddItemInfo(ThumbInfo url , SeriesModel model);
    void OnFinishBrowseThread(MainActivity.eActionType actionType);
}
