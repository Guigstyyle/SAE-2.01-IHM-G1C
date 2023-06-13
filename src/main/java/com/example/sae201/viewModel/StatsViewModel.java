/**
 * The StatsViewModel class represents the view model for the statistics view in the application.
 * It handles the interaction between the statistics view and the data manager.
 */

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

    /**
     * Constructs a StatsViewModel object with the specified SceneManager.
     *
     * @param sceneManager the SceneManager object to be associated with the view model
     */
    public StatsViewModel(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.dataManager = Main.getDataManager();
    }

    /**
     * Handles the button click event with the specified button ID.
     *
     * @param btnID the ID of the button clicked
     */
    public void handleButton(String btnID) {
        sceneManager.showScene(btnID);
    }

    /**
     * Retrieves the filtered data based on the provided search criteria.
     *
     * @param searchData the map containing the search criteria
     * @return the filtered data as an ObservableList
     */
    public ObservableList<Data> getFilteredData(ObservableMap<String, Object> searchData) {
        return dataManager.filterData(searchData);
    }

    /**
     * Retrieves the previously filtered data.
     *
     * @return the previously filtered data as an ObservableList
     */
    public ObservableList<Data> getFilteredData() {
        return dataManager.getFilteredData();
    }

    /**
     * Retrieves a list of all departments.
     *
     * @return the list of all departments as an ObservableList
     */
    public  ObservableList<String> getAllDepartments() {
        ObservableList<String> departments = FXCollections.observableArrayList();
        departments.addAll(dataManager.getFrenchDepartments().keySet());
        return departments;
    }

    /**
     * Retrieves the search data containing the current search criteria.
     *
     * @return the search data as an ObservableMap
     */
    public ObservableMap<String, Object> getSearchData() {
        return dataManager.getSearchData();
    }

    /**
     * Retrieves the property representing the updated state of the search data.
     *
     * @return the BooleanProperty representing the updated state of the search data
     */
    public BooleanProperty getSearchDataUpdatedProperty() {
        return dataManager.searchDataUpdatedProperty();
    }

    /**
     * Retrieves the property representing the filtered state of the data.
     *
     * @return the BooleanProperty representing the filtered state of the data
     */
    public BooleanProperty getDataFilteredProperty() {
        return dataManager.dataFilteredProperty();
    }

    /**
     * Retrieves the binding representing the search and filter state.
     *
     * @return the BooleanBinding representing the search and filter state
     */
    public BooleanBinding getSearchAndFilerBinding() {
        return dataManager.searchAndFilterBindingProperty();
    }

    /**
     * Retrieves the intensity per year data.
     *
     * @return the intensity per year data as an ObservableList
     */
    public ObservableList<YearIntensityData> getIntensityPerYearData() {
        return dataManager.getIntensityPerYearData();
    }

    /**
     * Retrieves the Richter intensity data.
     *
     * @return the Richter intensity data as a Map
     */
    public Map<String, Integer> getRichterIntensityData() {
        return dataManager.getRichterIntensityData();
    }
}
