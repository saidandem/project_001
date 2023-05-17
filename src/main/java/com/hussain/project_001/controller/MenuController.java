package com.hussain.project_001.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

/**
 * Controller implementation for menu.fxml.
 */
public class MenuController extends AbstractController{
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(MenuController.class.getName());

    @FXML
    private BorderPane menuPane;

    /**
     * Constructor.
     */
    public MenuController() {
        super(MenuController.class.getResource("menu.fxml"));
    }

    @Override
    public void onControllerLoad() {
        LOGGER.info("MenuController : onControllerLoad....");
    }
}
