package com.example.sae201.view;

import com.example.sae201.Main;
import com.example.sae201.model.Data;
import com.example.sae201.viewModel.StatsViewModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.controlsfx.control.RangeSlider;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable {
    @FXML
    private VBox root;
    @FXML
    private TextField dateMin;
    @FXML
    private TextField dateMax;
    @FXML
    private RangeSlider magnitudeSlider;
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> department;
    @FXML
    private TableView dataTable;
    private StatsViewModel statsViewModel;
    private ObservableList<Data> dataList;
    private ObservableMap<String, Object> searchData;
    private BooleanProperty searchDataUpdated;
    private BooleanProperty dataFiltered;
    private BooleanBinding searchAndFilterBinding;

    public StatsController() {
        SceneManager sceneManager = Main.getSceneManager();
        statsViewModel = new StatsViewModel(sceneManager);
        dataList = statsViewModel.getFilteredData();
        searchData = statsViewModel.getSearchData();
        searchDataUpdated = statsViewModel.getSearchDataUpdatedProperty();
        dataFiltered = statsViewModel.getDataFilteredProperty();
        searchAndFilterBinding = statsViewModel.getSearchAndFilerBinding();
    }

    @FXML
    public void navButtonsHandler(ActionEvent event) {
        Button btnUsed = (Button) event.getSource();
        String btnID = btnUsed.getId();

        statsViewModel.handleButton(btnID);
    }

    @FXML
    public void searchButtonHandler(ActionEvent event) {
        searchData.put("dateMin", this.dateMin.getText());
        searchData.put("dateMax", this.dateMax.getText());
        searchData.put("intensityMin", this.magnitudeSlider.getLowValue());
        searchData.put("intensityMax", this.magnitudeSlider.getHighValue());
        searchData.put("country", this.country.getValue());
        searchData.put("department", this.department.getValue());

        searchDataUpdated.set(true);

        dataList = statsViewModel.getFilteredData(searchData);

        System.out.println("dateMin: " + searchData.get("dateMin"));
        System.out.println("dateMax: " + searchData.get("dateMax"));
        System.out.println("intensityMin: " + searchData.get("intensityMin"));
        System.out.println("intensityMax: " + searchData.get("intensityMax"));
        System.out.println("country: " + searchData.get("country"));
        System.out.println("department: " + searchData.get("department"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchAndFilterBinding.addListener((observable, oldValue, newValue) -> {
            this.dateMin.setText((String) searchData.get("dateMin"));
            this.dateMax.setText((String) searchData.get("dateMax"));
            this.magnitudeSlider.setLowValue((Double) searchData.get("intensityMin"));
            this.magnitudeSlider.setHighValue((Double) searchData.get("intensityMax"));
            this.country.setValue((String) searchData.get("country"));
            this.department.setValue((String) searchData.get("department"));
            System.out.println("\u001B[32mStatsController:\nSearch Data: Updated\nData: Filtered\u001B[0m");
            searchDataUpdated.set(false);
            dataFiltered.set(false);
        });

        department.setItems(statsViewModel.getAllDepartments());
        department.getItems().add(0, "Département");
    }
}
