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

    public HashMap<String, Double> getAverageLatitudeAndLongitude(ObservableList<Data> dataList) {
        HashMap<String, Double> results = new HashMap<>();
        double sumLatitude = 0;
        double sumLongitude = 0;
        int count = 0;

        for (Data data : dataList) {
            Double latitude = data.getLatitude();
            Double longitude = data.getLongitude();

            if (latitude != null && longitude != null) {
                sumLatitude += latitude;
                sumLongitude += longitude;
                count += 1;
            }
        }

        if (count > 0) {
            results.put("averageLatitude", sumLatitude / count);
            results.put("averageLongitude", sumLongitude / count);
        }

        return results;
    }
}
