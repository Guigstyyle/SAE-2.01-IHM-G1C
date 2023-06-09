package com.example.sae201.view;

import com.example.sae201.Main;
import com.example.sae201.model.Data;
import com.example.sae201.model.MapRectangleLayer;
import com.example.sae201.viewModel.MapViewModel;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.controlsfx.control.RangeSlider;

import java.net.URL;
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
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> department;
    @FXML
    private TableView dataTable;
    @FXML
    private MapView map;

    private MapViewModel mapViewModel;
    private ObservableList<Data> dataList;
    private ObservableList<MapLayer> mapLayers;


    public MapController() {
        SceneManager sceneManager = Main.getSceneManager();
        mapViewModel = new MapViewModel(sceneManager);
        map = new MapView();
        dataList = FXCollections.observableArrayList();
        mapLayers = FXCollections.observableArrayList();
    }

    @FXML
    public void navButtonsHandler(ActionEvent event) {
        Button btnUsed = (Button) event.getSource();
        String btnID = btnUsed.getId();

        mapViewModel.handleButton(btnID);
    }

    @FXML
    public void searchButtonHandler(ActionEvent event) {
        HashMap<String, Object> searchData = new HashMap<>();

        searchData.put("dateMin", this.dateMin.getText());
        searchData.put("dateMax", this.dateMax.getText());
        searchData.put("intensityMin", this.magnitudeSlider.getLowValue());
        searchData.put("intensityMax", this.magnitudeSlider.getHighValue());
        searchData.put("country", this.country.getValue());
        searchData.put("department", this.department.getValue());

        dataList = mapViewModel.getFilteredData(searchData);

        dataTable.setItems(dataList);

        if (mapLayers.size() != 0) {
            for (MapLayer mapLayer : mapLayers) {
                map.removeLayer(mapLayer);
            }
        }

        for (Data data : dataList) {
            Double latitude = data.getLatitude();
            Double longitude = data.getLongitude();

            if (latitude != null && longitude != null) {
                MapPoint mapPoint = new MapPoint(latitude, longitude);
                MapLayer mapLayer = new MapRectangleLayer(mapPoint);

                mapLayers.add(mapLayer);
                map.addLayer(mapLayer);
            }
        }

        System.out.println("dateMin: " + searchData.get("dateMin"));
        System.out.println("dateMax: " + searchData.get("dateMax"));
        System.out.println("intensityMin: " + searchData.get("intensityMin"));
        System.out.println("intensityMax: " + searchData.get("intensityMax"));
        System.out.println("country: " + searchData.get("country"));
        System.out.println("department: " + searchData.get("department"));
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
        TableColumn<Data, Double> latitudeColumn = new TableColumn<>("Latitude");
        TableColumn<Data, Double> longitudeColumn = new TableColumn<>("Longitude");
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
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        intensityValueColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        intensityQualityColumn.setCellValueFactory(new PropertyValueFactory<>("intensityQuality"));

        dataTable.getColumns().addAll(idColumn, dateColumn, timeColumn, nameColumn, regionColumn,
                shockColumn, intensityColumn, xRGF93Column, yRGF93Column, latitudeColumn, longitudeColumn,
                intensityValueColumn, intensityQualityColumn);
    }

    private void initializeMap() {
        map.setCenter(46.6, 1.88);
        map.setZoom(5.8);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        initializeMap();
    }
}
