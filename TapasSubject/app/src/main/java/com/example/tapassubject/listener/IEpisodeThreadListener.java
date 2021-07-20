package com.example.tapassubject.listener;

import com.example.tapassubject.model.EpisodeModel;

import java.util.List;

public interface IEpisodeThreadListener {
    void OnFinishEpisodeThread(List<EpisodeModel> models);
}
