package com.example.sae201.viewModel;

import com.example.sae201.view.SceneManager;

public class MapViewModel {
    private SceneManager sceneManager;

    public MapViewModel(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void handleButton(String btnID) {
        sceneManager.showScene(btnID);
    }
}
