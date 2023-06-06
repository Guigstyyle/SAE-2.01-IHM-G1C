package com.example.sae201.controllers;

import com.example.sae201.AppMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private Stage stage;
    private Map<String, Scene> scenes;

    public SceneManager(Stage primaryStage) {
        this.stage = primaryStage;
        scenes = new HashMap<>();
    }

    public void preloadScene(String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource(fileName + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scenes.put(fileName, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showScene(String fileName) {
        Scene scene = scenes.get(fileName);
        if (scene != null) {
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Scene not preloaded: " + fileName);
        }
    }
}
