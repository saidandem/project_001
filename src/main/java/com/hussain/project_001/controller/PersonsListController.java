package com.hussain.project_001.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.logging.Logger;

public class PersonsListController extends AbstractController {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    @FXML
    private BorderPane personsListPane;

    @FXML
    private StackPane searchPane;

    @FXML
    private HBox searchBox;

    @FXML
    private TextField searchField;

    @FXML
    private StackPane searchIcon;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowPane;

    /**
     * Constructor.
     */
    public PersonsListController() {
        super(PersonsListController.class.getResource("personsList.fxml"));
    }

    @Override
    public void onControllerLoad() {
        LOGGER.info("Loaded PersonsListController");
        initSearchField();
    }

    private void initSearchField() {
        if (searchField.getLength() == 0) {
            searchIcon.getStyleClass().add("search-magnifying-glass");
        }

        searchField.textProperty().addListener((ov, oldStr, newStr) -> {
            if (newStr.isEmpty()) {
                searchIcon.getStyleClass().setAll("search-magnifying-glass");
            } else {
                searchIcon.getStyleClass().setAll("search-clear");
            }
        });

        searchField.setOnAction(e -> {
            // Do some action
        });

        searchField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                searchField.clear();
            }
        });

        searchIcon.setOnMouseClicked(t -> {
            searchField.clear();
            searchField.getOnAction().handle(null);
        });
    }
}
