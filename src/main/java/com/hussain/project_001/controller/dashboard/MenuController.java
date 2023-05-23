package com.hussain.project_001.controller.dashboard;

import com.hussain.project_001.controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.util.logging.Logger;

/**
 * Controller implementation for menu.fxml.
 */
public class MenuController extends AbstractController {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MenuController.class.getName());

    @FXML
    private BorderPane menuPane;

    @FXML
    private Button homeButton;

    @FXML
    private Button peopleButton;

    @FXML
    private Button settingsButton;


    private DashboardController dashboardController;

    /**
     * Constructor.
     */
    public MenuController() {
        super(MenuController.class.getResource("menu.fxml"));
    }

    @Override
    public void onControllerLoad() {
        LOGGER.info("MenuController : onControllerLoad....");
        homeButton.setOnAction(this::showUtitlities);
        peopleButton.setOnAction(this::showPersons);
        settingsButton.setOnAction(this::showSettings);
    }

    private void showSettings(final ActionEvent actionEvent) {
        dashboardController.showSettings();
    }

    @FXML
    public void showUtitlities(ActionEvent event) {
        dashboardController.showUtilities();
    }

    @FXML
    public void showPersons(ActionEvent event) {
        dashboardController.showPersons();
    }

    public void setDashboardController(final DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
}
