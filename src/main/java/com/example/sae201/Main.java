package com.example.sae201;

import com.example.sae201.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static SceneManager sceneManager;

    public static SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("SAE");

        sceneManager = new SceneManager(stage);

        sceneManager.preloadScene("MapView");
        sceneManager.preloadScene("StatsView");

        sceneManager.showScene("MapView");
    }

    public static void main(String[] args) {
        launch();
    }
}
