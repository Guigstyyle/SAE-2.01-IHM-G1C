package com.example.sae201;

import com.example.sae201.model.DataManager;
import com.example.sae201.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static SceneManager sceneManager;
    private static DataManager dataManager;

    public static SceneManager getSceneManager() {
        return sceneManager;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("SAE");

        dataManager = new DataManager();
        sceneManager = new SceneManager(stage);

        dataManager.loadData();
        sceneManager.preloadScene("MapView");
        sceneManager.preloadScene("StatsView");

        sceneManager.showScene("MapView");
    }

    public static void main(String[] args) {
        launch();
    }
}
