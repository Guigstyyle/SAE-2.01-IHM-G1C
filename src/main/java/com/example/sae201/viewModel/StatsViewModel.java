package com.example.sae201.viewModel;

import com.example.sae201.view.SceneManager;

public class StatsViewModel {
    private SceneManager sceneManager;

    public StatsViewModel(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void handleButton(String btnID) {
        sceneManager.showScene(btnID);
    }
}
