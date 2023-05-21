package com.hussain.project_001.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NavigationPaneController extends AbstractController{

    @FXML
    private Button backButton;

    private Runnable backHandler;

    public NavigationPaneController() {
        super(NavigationPaneController.class.getResource("navigationPane.fxml"));
    }

    public void setBackHandler(final Runnable backHandler) {
        this.backHandler = backHandler;
    }

    @Override
    public void onControllerLoad() {
        backButton.setOnAction(e -> {
            if (backHandler != null) {
                backHandler.run();
            }
        });
    }
}
