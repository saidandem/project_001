package com.hussain.project_001.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.function.Consumer;

/**
 * Controller implementation for the searchPane.fxml.
 */
public class SearchPaneController  {

    @FXML
    private StackPane searchPane;

    @FXML
    private TextField searchField;

    @FXML
    private StackPane searchIcon;

    private Consumer<String> searchHandler;

    @FXML
    public void initialize(){
        if (searchField.getLength() == 0) {
            searchIcon.getStyleClass().add("search-magnifying-glass");
        }

        searchField.textProperty().addListener((ov, oldStr, newStr) -> {
            if (newStr.isEmpty()) {
                searchIcon.getStyleClass().setAll("search-magnifying-glass");
            } else {
                searchIcon.getStyleClass().setAll("search-clear");
            }
            search();
        });

        searchField.setOnAction(e -> search());

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

    private void search(){
        if(searchHandler!=null){
            searchHandler.accept(searchField.getText());
        }
    }

    public void setSearchHandler(final Consumer<String> searchHandler) {
        this.searchHandler = searchHandler;
    }

    public void setPromptText(String text){
        searchField.setPromptText(text);
    }
}
