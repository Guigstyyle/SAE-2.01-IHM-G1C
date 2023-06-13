package com.example.sae201;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class BarChartExample extends Application {
    private ObservableList<String> intensityRanges = FXCollections.observableArrayList(
            "0-2", "2-3", "3-4", "4-5", "5-6", "6-7", "8+"
    );

    private ObservableList<XYChart.Data<String, Number>> intensityData = FXCollections.observableArrayList(
            new XYChart.Data<>("0-2", 10),
            new XYChart.Data<>("2-3", 15),
            new XYChart.Data<>("3-4", 8),
            new XYChart.Data<>("4-5", 12),
            new XYChart.Data<>("5-6", 6),
            new XYChart.Data<>("6-7", 20),
            new XYChart.Data<>("8+", 5)
    );

    @Override
    public void start(Stage stage) {
        // Create the x-axis and y-axis
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Create the BarChart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Set the data to the BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>(intensityData);
        barChart.getData().add(series);

        // Set the title and labels
        barChart.setTitle("Intensity Data");
        xAxis.setLabel("Intensity Range");
        yAxis.setLabel("Count");

        // Show the BarChart
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
