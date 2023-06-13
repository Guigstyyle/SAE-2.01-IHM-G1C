package com.example.sae201.viewModel;

import com.example.sae201.Main;
import com.example.sae201.model.Data;
import com.example.sae201.model.DataManager;
import com.example.sae201.model.YearIntensityData;
import com.example.sae201.view.SceneManager;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Map;


public class StatsViewModel {
    private SceneManager sceneManager;
    private DataManager dataManager;

    public StatsViewModel(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.dataManager = Main.getDataManager();
    }

    public void handleButton(String btnID) {
        sceneManager.showScene(btnID);
    }

    public ObservableList<Data> getFilteredData(ObservableMap<String, Object> searchData) {
        return dataManager.filterData(searchData);
    }

    public ObservableList<Data> getFilteredData() {
        return dataManager.getFilteredData();
    }

    public  ObservableList<String> getAllDepartments() {
        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.addAll(dataManager.getFrenchDepartments().keySet());
        return departments;
    }

    public ObservableMap<String, Object> getSearchData() {
        return dataManager.getSearchData();
    }

    public BooleanProperty getSearchDataUpdatedProperty() {
        return dataManager.searchDataUpdatedProperty();
    }

    public BooleanProperty getDataFilteredProperty() {
        return dataManager.dataFilteredProperty();
    }

    public BooleanBinding getSearchAndFilerBinding() {
        return dataManager.searchAndFilterBindingProperty();
    }

    public ObservableList<YearIntensityData> getIntensityPerYearData() {
        return dataManager.getIntensityPerYearData();
    }

    public Map<String, Integer> getRichterIntensityData() {
        return dataManager.getRichterIntensityData();
    }
}
