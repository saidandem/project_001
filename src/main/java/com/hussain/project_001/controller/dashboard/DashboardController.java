package com.hussain.project_001.controller.dashboard;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.controller.persons.PersonsController;
import com.hussain.project_001.controller.utilities.UtilitiesController;
import com.hussain.project_001.model.Utility;
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

    private PersonsController personsListController;

    private UtilitiesController utilitiesListController;

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
        menuController.setDashboardController(this);
        dashboard.setLeft(menuController.getPanel());
        showUtilities();
    }

    public PersonsController getPersonsListController() {
        if (personsListController == null) {
            personsListController = new PersonsController();
            personsListController.setDashboardController(this);
        }
        return personsListController;
    }

    public UtilitiesController getUtilitiesListController() {
        if (utilitiesListController == null) {
            utilitiesListController = new UtilitiesController();
            utilitiesListController.setDashboardController(this);
        }
        return utilitiesListController;
    }

    public void showUtilities() {
        dashboard.setCenter(getUtilitiesListController().getPanel());
    }

    public void showPersons() {
        dashboard.setCenter(getPersonsListController().getPanel());
    }
}
