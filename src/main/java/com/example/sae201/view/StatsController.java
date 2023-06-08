package com.example.sae201.view;

import com.example.sae201.Main;
import com.example.sae201.viewModel.StatsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StatsController {
    @FXML
    private VBox root;
    private StatsViewModel statsViewModel;

    public StatsController() {
        SceneManager sceneManager = Main.getSceneManager();
        statsViewModel = new StatsViewModel(sceneManager);
    }

    @FXML
    public void navButtonsHandler(ActionEvent event) {
        Button btnUsed = (Button) event.getSource();
        String btnID = btnUsed.getId();

        statsViewModel.handleButton(btnID);
    }
}
