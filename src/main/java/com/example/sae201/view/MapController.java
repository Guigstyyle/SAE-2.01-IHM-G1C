package com.example.sae201.view;

import com.example.sae201.Main;
import com.example.sae201.model.Data;
import com.example.sae201.model.DataManager;
import com.example.sae201.model.MapCircleLayer;
import com.example.sae201.viewModel.MapViewModel;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MapController implements Initializable {
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
    private TableView dataTable;
    @FXML
    private MapView map;

    private MapViewModel mapViewModel;
    private ObservableList<Data> dataList;
    private ObservableMap<String, Object> searchData;
    private ObservableList<MapLayer> mapLayers;
    private BooleanProperty searchDataUpdated;
    private BooleanProperty dataFiltered;
    private BooleanBinding searchAndFilterBinding;


    public MapController() {
        SceneManager sceneManager = Main.getSceneManager();
        mapViewModel = new MapViewModel(sceneManager);
        map = new MapView();
        dataList = mapViewModel.getOldFilteredData();
        mapLayers = FXCollections.observableArrayList();
        searchData = mapViewModel.getSearchData();
        searchDataUpdated = mapViewModel.getSearchDataUpdatedProperty();
        dataFiltered = mapViewModel.getDataFilteredProperty();
        searchAndFilterBinding = mapViewModel.getSearchAndFilerBinding();
    }

    @FXML
    public void navButtonsHandler(ActionEvent event) {
        Button btnUsed = (Button) event.getSource();
        String btnID = btnUsed.getId();

        mapViewModel.handleButton(btnID);
    }

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

    @FXML
    public void searchButtonHandler(ActionEvent event) {
        searchData.put("dateMin", this.dateMin.getText());
        searchData.put("dateMax", this.dateMax.getText());
        searchData.put("intensityMin", this.magnitudeSlider.getLowValue());
        searchData.put("intensityMax", this.magnitudeSlider.getHighValue());
        searchData.put("country", null);
        searchData.put("department", this.department.getValue());

        searchDataUpdated.set(true);

        dataList = mapViewModel.getFilteredData(searchData);

        System.out.println("dateMin: " + searchData.get("dateMin"));
        System.out.println("dateMax: " + searchData.get("dateMax"));
        System.out.println("intensityMin: " + searchData.get("intensityMin"));
        System.out.println("intensityMax: " + searchData.get("intensityMax"));
        System.out.println("country: " + searchData.get("country"));
        System.out.println("department: " + searchData.get("department"));
    }

    private void renderMap() {
        System.out.println("- RenderMap: Started!");
        if (mapLayers.size() != 0) {
            for (MapLayer mapLayer : mapLayers) {
                map.removeLayer(mapLayer);
            }
            mapLayers.clear();
        }

        for (Data data : dataList) {
            Double latitude = data.getLatitude();
            Double longitude = data.getLongitude();

            if (latitude != null && longitude != null) {
                MapPoint mapPoint = new MapPoint(latitude, longitude);
                MapLayer mapLayer = new MapCircleLayer(mapPoint);

                mapLayers.add(mapLayer);
                map.addLayer(mapLayer);
            }
        }

        if (searchData.get("department") != null && !searchData.get("department").equals("Département") && dataList.size() != 0) {
            HashMap<String, Double> averageLatitudeAndLongitude = mapViewModel.getAverageLatitudeAndLongitude(dataList);
            map.setZoom(7);
            map.flyTo(0.1, new MapPoint(averageLatitudeAndLongitude.get("averageLatitude"), averageLatitudeAndLongitude.get("averageLongitude")), 0.8);
        } else {
            map.setZoom(5.8);
            map.flyTo(0.1, new MapPoint(46.6, 1.88), 0.8);
        }
        System.out.println("- RenderMap: Ended!");
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

    private void initializeMap() {
        map.setCenter(46.6, 1.88);
        map.setZoom(5.8);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchAndFilterBinding.addListener((observable, oldValue, newValue) -> {
            this.dateMin.setText((String) searchData.get("dateMin"));
            this.dateMax.setText((String) searchData.get("dateMax"));
            this.magnitudeSlider.setLowValue((Double) searchData.get("intensityMin"));
            this.magnitudeSlider.setHighValue((Double) searchData.get("intensityMax"));
            this.department.setValue((String) searchData.get("department"));
            renderMap();
            System.out.println("\u001B[32mMapController:\nSearch Data: Updated\nData: Filtered\nMap Rendered\u001B[0m");
            searchDataUpdated.set(false);
            dataFiltered.set(false);
        });

        initializeTable();
        initializeMap();
        department.setItems(mapViewModel.getAllDepartments());
        department.getItems().add(0, "Département");
    }
}
