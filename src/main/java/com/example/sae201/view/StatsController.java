package com.example.sae201.view;

import com.example.sae201.Main;
import com.example.sae201.model.Data;
import com.example.sae201.model.YearIntensityData;
import com.example.sae201.viewModel.StatsViewModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    private LineChart<String, Number> lineChart;
    private CategoryAxis xAxisLineChart;
    private NumberAxis yAxisLineChart;
    @FXML
    private TableView dataTable;
    private StatsViewModel statsViewModel;
    private ObservableList<Data> dataList;
    private ObservableMap<String, Object> searchData;
    private BooleanProperty searchDataUpdated;
    private BooleanProperty dataFiltered;
    private BooleanBinding searchAndFilterBinding;
    private ObservableList<YearIntensityData> intensityPerYearData;

    public StatsController() {
        SceneManager sceneManager = Main.getSceneManager();
        statsViewModel = new StatsViewModel(sceneManager);
        dataList = statsViewModel.getFilteredData();
        searchData = statsViewModel.getSearchData();
        searchDataUpdated = statsViewModel.getSearchDataUpdatedProperty();
        dataFiltered = statsViewModel.getDataFilteredProperty();
        searchAndFilterBinding = statsViewModel.getSearchAndFilerBinding();
        intensityPerYearData = statsViewModel.getIntensityPerYearData();
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

    private void renderLineChart() {
        intensityPerYearData = statsViewModel.getIntensityPerYearData();

        lineChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Intensité");

        for (YearIntensityData data : intensityPerYearData) {
            if (data.getMean() == null || data.getYear() == null) {
                continue;
            }
            series.getData().add(new XYChart.Data<>(data.getYear(), data.getMean()));
        }

        lineChart.getData().add(series);
    }

    private void initializeLineChart() {
        xAxisLineChart = (CategoryAxis) lineChart.getXAxis();
        yAxisLineChart = (NumberAxis) lineChart.getYAxis();

        xAxisLineChart.setLabel("Dates (Année)");
        yAxisLineChart.setLabel("Intensité (Richter)");
        lineChart.setTitle("Intensité moyenne par Année");
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

            renderLineChart();

            System.out.println("\u001B[32mStatsController:\nSearch Data: Updated\nData: Filtered\u001B[0m");
            searchDataUpdated.set(false);
            dataFiltered.set(false);
        });

        initializeLineChart();

        department.setItems(statsViewModel.getAllDepartments());
        department.getItems().add(0, "Département");
    }
}
