/**
 * The Main class is the entry point of the application and extends the JavaFX Application class.
 * It initializes the scene manager and data manager, loads data, and starts the application.
 */

package com.example.sae201;

import com.example.sae201.model.DataManager;
import com.example.sae201.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static SceneManager sceneManager;
    private static DataManager dataManager;

    /**
     * Retrieves the scene manager instance.
     *
     * @return the SceneManager instance
     */
    public static SceneManager getSceneManager() {
        return sceneManager;
    }

    /**
     * Retrieves the data manager instance.
     *
     * @return the DataManager instance
     */
    public static DataManager getDataManager() {
        return dataManager;
    }

    /**
     * Starts the JavaFX application by initializing the stage, scene manager, and data manager.
     * It loads data, preloads scenes, and shows the initial scene.
     *
     * @param stage the primary stage for the JavaFX application
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");

        stage.setTitle("SAE");

        dataManager = new DataManager();
        sceneManager = new SceneManager(stage);

        dataManager.loadData();
        dataManager.loadDepData();
        sceneManager.preloadScene("MapView");
        sceneManager.preloadScene("StatsView");

        sceneManager.showScene("MapView");
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
