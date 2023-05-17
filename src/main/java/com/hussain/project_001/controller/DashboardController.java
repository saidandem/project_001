package com.hussain.project_001.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.util.logging.Logger;

/**
 * Controller implementation for the dashboard.fxml.
 */
public class DashboardController extends AbstractController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    @FXML
    private BorderPane dashboard;

    private MenuController menuController;

    private PersonsListController personsListController;

    /**
     * Constructor.
     */
    public DashboardController() {
        super(DashboardController.class.getResource("dashboard.fxml"));
    }

    @Override
    public void onControllerLoad() {
        LOGGER.info("Loaded DashboardController");
        menuController = new MenuController();
        dashboard.setLeft(menuController.getPanel());

        personsListController = new PersonsListController();
        dashboard.setCenter(personsListController.getPanel());
    }
}
