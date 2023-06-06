package com.example.sae201.controllers;

import com.example.sae201.AppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapController {
    private SceneManager sceneManager = AppMain.getSceneManager();

    @FXML
    public void buttonsHandler(ActionEvent event) {
        Button btnUsed = (Button) event.getSource();
        String btnID = btnUsed.getId();

        sceneManager.showScene(btnID);
    }
}
