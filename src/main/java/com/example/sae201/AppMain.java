package com.example.sae201;

import com.example.sae201.controllers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class AppMain extends Application {
    private static SceneManager sceneManager;

    public static SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("SAE");

        sceneManager = new SceneManager(stage);

        sceneManager.preloadScene("mapView");
        sceneManager.preloadScene("statsView");

        sceneManager.showScene("mapView");
    }

    public static void main(String[] args) {
        launch();
    }
}
