package com.example.sae201.view;

import com.example.sae201.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The SceneManager class is responsible for managing scenes in a JavaFX application.
 */
public class SceneManager {
    private Stage stage;
    private Map<String, Scene> scenes;

    /**
     * Constructs a new SceneManager object.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    public SceneManager(Stage primaryStage) {
        this.stage = primaryStage;
        scenes = new HashMap<>();
    }

    /**
     * Preloads a scene by loading the FXML file and creating a scene object.
     *
     * @param fileName the name of the FXML file to preload
     */
    public void preloadScene(String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/" + fileName + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scenes.put(fileName, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a preloaded scene on the primary stage.
     *
     * @param fileName the name of the preloaded scene to show
     */
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
