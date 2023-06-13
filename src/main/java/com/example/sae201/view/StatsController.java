package com.example.sae201.view;

import com.example.sae201.Main;
import com.example.sae201.model.Data;
import com.example.sae201.model.YearIntensityData;
import com.example.sae201.viewModel.StatsViewModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.RangeSlider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The StatsController class handles the logic and user interactions for the statistics view in a JavaFX application.
 */
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
    private ComboBox<String> department;
    @FXML
    private LineChart<String, Number> lineChart;
    private CategoryAxis xAxisLineChart;
    private CategoryAxis xAxisBarChart;
    private NumberAxis yAxisLineChart;
    private NumberAxis yAxisBarChart;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private TableView dataTable;
    private StatsViewModel statsViewModel;
    private ObservableList<Data> dataList;
    private ObservableMap<String, Object> searchData;
    private BooleanProperty searchDataUpdated;
    private BooleanProperty dataFiltered;
    private BooleanBinding searchAndFilterBinding;
    private ObservableList<YearIntensityData> intensityPerYearData;
    private Map<String, Integer> intensityData;

    /**
     * Constructs a new StatsController object.
     */
    public StatsController() {
        SceneManager sceneManager = Main.getSceneManager();
        statsViewModel = new StatsViewModel(sceneManager);
        dataList = statsViewModel.getFilteredData();
        searchData = statsViewModel.getSearchData();
        searchDataUpdated = statsViewModel.getSearchDataUpdatedProperty();
        dataFiltered = statsViewModel.getDataFilteredProperty();
        searchAndFilterBinding = statsViewModel.getSearchAndFilerBinding();
        intensityPerYearData = statsViewModel.getIntensityPerYearData();
        intensityData = statsViewModel.getRichterIntensityData();
    }

    /**
     * Event handler for navigation buttons.
     *
     * @param event the action event
     */
    @FXML
    public void navButtonsHandler(ActionEvent event) {
        Button btnUsed = (Button) event.getSource();
        String btnID = btnUsed.getId();

        statsViewModel.handleButton(btnID);
    }

    /**
     * Event handler for the import button.
     *
     * @param event the action event
     */
    @FXML
    public void importButtonHandler(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        try {
            if (file != null) {
                Main.getDataManager().importCSV(file.toURL());
                Main.getDataManager().loadData();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the search button.
     *
     * @param event the action event
     */
    @FXML
    public void searchButtonHandler(ActionEvent event) {
        searchData.put("dateMin", this.dateMin.getText());
        searchData.put("dateMax", this.dateMax.getText());
        searchData.put("intensityMin", this.magnitudeSlider.getLowValue());
        searchData.put("intensityMax", this.magnitudeSlider.getHighValue());
        searchData.put("country", null);
        searchData.put("department", this.department.getValue());

        searchDataUpdated.set(true);

        dataList = statsViewModel.getFilteredData(searchData);
        intensityData = statsViewModel.getRichterIntensityData();

        System.out.println("dateMin: " + searchData.get("dateMin"));
        System.out.println("dateMax: " + searchData.get("dateMax"));
        System.out.println("intensityMin: " + searchData.get("intensityMin"));
        System.out.println("intensityMax: " + searchData.get("intensityMax"));
        System.out.println("country: " + searchData.get("country"));
        System.out.println("department: " + searchData.get("department"));

        System.out.println("0-2: " + intensityData.get("0-2"));
        System.out.println("2-3: " + intensityData.get("2-3"));
        System.out.println("3-4: " + intensityData.get("3-4"));
        System.out.println("4-5: " + intensityData.get("4-5"));
        System.out.println("5-6: " + intensityData.get("5-6"));
        System.out.println("6-7: " + intensityData.get("6-7"));
        System.out.println("8+: " + intensityData.get("8+"));
    }

    /**
     * Renders the line chart with the intensity per year data.
     */
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

    /**
     * Renders the bar chart with the intensity data.
     */
    private void renderBarChart() {
        intensityData = statsViewModel.getRichterIntensityData();

        barChart.getData().clear();

        ObservableList<XYChart.Data<String, Number>> test = FXCollections.observableArrayList(
                new XYChart.Data<>("0-2", intensityData.get("0-2")),
                new XYChart.Data<>("2-3", intensityData.get("2-3")),
                new XYChart.Data<>("3-4", intensityData.get("3-4")),
                new XYChart.Data<>("4-5", intensityData.get("4-5")),
                new XYChart.Data<>("5-6", intensityData.get("5-6")),
                new XYChart.Data<>("6-7", intensityData.get("6-7")),
                new XYChart.Data<>("8+", intensityData.get("8+"))
        );

        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>(test);
        barChart.getData().add(series);
    }

    /**
     * Initializes the line chart.
     */
    private void initializeLineChart() {
        xAxisLineChart = (CategoryAxis) lineChart.getXAxis();
        yAxisLineChart = (NumberAxis) lineChart.getYAxis();

        xAxisLineChart.setLabel("Dates (Année)");
        yAxisLineChart.setLabel("Intensité");
        lineChart.setTitle("Intensité moyenne par Année");
    }

    /**
     * Initializes the bar chart.
     */
    public void initializeBarChart() {
        xAxisBarChart = (CategoryAxis) barChart.getXAxis();
        yAxisBarChart = (NumberAxis) barChart.getYAxis();

        xAxisBarChart.setLabel("Intensité de seisme");
        yAxisBarChart.setLabel("Nombres des seismes");
        barChart.setTitle("Nombres de seisme par Intensité");
    }

    /**
     * Initializes the table view.
     */
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
            this.department.setValue((String) searchData.get("department"));

            renderLineChart();
            renderBarChart();

            System.out.println("\u001B[32mStatsController:\nSearch Data: Updated\nData: Filtered\u001B[0m");
            searchDataUpdated.set(false);
            dataFiltered.set(false);
        });

        initializeLineChart();
        initializeTable();
        initializeBarChart();

        department.setItems(statsViewModel.getAllDepartments());
        department.getItems().add(0, "Département");
    }
}
