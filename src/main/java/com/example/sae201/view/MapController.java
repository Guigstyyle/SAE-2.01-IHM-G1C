package com.example.sae201.view;

import com.example.sae201.Main;
import com.example.sae201.viewModel.MapViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MapController {
    @FXML
    private VBox root;
    private MapViewModel mapViewModel;

    public MapController() {
        SceneManager sceneManager = Main.getSceneManager();
        mapViewModel = new MapViewModel(sceneManager);
    }

    @FXML
    public void buttonsHandler(ActionEvent event) {
        Button btnUsed = (Button) event.getSource();
        String btnID = btnUsed.getId();

        mapViewModel.handleButton(btnID);
    }
}
