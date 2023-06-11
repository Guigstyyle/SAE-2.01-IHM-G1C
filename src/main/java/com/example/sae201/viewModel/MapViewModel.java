package com.example.sae201.viewModel;

import com.example.sae201.Main;
import com.example.sae201.model.Data;
import com.example.sae201.model.DataManager;
import com.example.sae201.view.SceneManager;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class MapViewModel {
    private SceneManager sceneManager;
    private DataManager dataManager;

    public MapViewModel(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.dataManager = Main.getDataManager();
    }

    public void handleButton(String btnID) {
        sceneManager.showScene(btnID);
    }

    public ObservableList<Data> getFilteredData(HashMap<String, Object> searchData) {
        return dataManager.filterData(searchData);
    }

    public ObservableList<String> getAllRegions() {
        return dataManager.getAllRegions();
    }
}
