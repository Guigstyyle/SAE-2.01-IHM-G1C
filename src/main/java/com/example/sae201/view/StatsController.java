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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.RangeSlider;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
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
    public void importButtonHandler(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        Path path = Path.of(file.getPath());

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

    private void initializeTable() {
        TableColumn<Data, String> idColumn = new TableColumn<>("id");
        TableColumn<Data, String> dateColumn = new TableColumn<>("Date (AAAA/MM/JJ)");
        TableColumn<Data, String> timeColumn = new TableColumn<>("Heure");
        TableColumn<Data, String> nameColumn = new TableColumn<>("Nom");
        TableColumn<Data, String> regionColumn = new TableColumn<>("Région épicentrale");
        TableColumn<Data, String> shockColumn = new TableColumn<>("Choc");
        TableColumn<Data, String> intensityColumn = new TableColumn<>("Intensité épicentrale");
        TableColumn<Data, Double> xRGF93Column = new TableColumn<>("xRGF93");
        TableColumn<Data, Double> yRGF93Column = new TableColumn<>("yRGF93");
        TableColumn<Data, Double> intensityValueColumn = new TableColumn<>("Intensité");
        TableColumn<Data, String> intensityQualityColumn = new TableColumn<>("Qualité de l'intensité");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        shockColumn.setCellValueFactory(new PropertyValueFactory<>("shock"));
        intensityColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        xRGF93Column.setCellValueFactory(new PropertyValueFactory<>("xRGF93"));
        yRGF93Column.setCellValueFactory(new PropertyValueFactory<>("yRGF93"));
        intensityValueColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        intensityQualityColumn.setCellValueFactory(new PropertyValueFactory<>("intensityQuality"));

        dataTable.getColumns().addAll(idColumn, dateColumn, timeColumn, nameColumn, regionColumn,
                shockColumn, intensityColumn, xRGF93Column, yRGF93Column,
                intensityValueColumn, intensityQualityColumn);

        dataTable.setItems(dataList);
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
        initializeTable();

        department.setItems(statsViewModel.getAllDepartments());
        department.getItems().add(0, "Département");
    }
}
