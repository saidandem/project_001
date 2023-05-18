package com.hussain.project_001.controller;

import com.hussain.project_001.model.Person;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.time.format.DateTimeFormatter;

public class PersonInfoTileController extends AbstractController {

    @FXML
    private StackPane infoPane;

    @FXML
    private Label dobLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label address1Label;

    @FXML
    private Label address2Label;

    @FXML
    private Label cityLabel;

    @FXML
    private Label initialsLabel;

    @FXML
    private Circle levelCircle;

    @FXML
    private Label zipLabel;

    private Person person;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PersonInfoTileController(Person person) {
        super(PersonInfoTileController.class.getResource("personInfoTile.fxml"));
        this.person = person;
    }

    @Override
    public void onControllerLoad() {
        bindModel();
    }

    private void bindModel() {
        dobLabel.textProperty().bind(Bindings.createStringBinding(() -> person.getDateOfBirth().format(formatter), person.dateOfBirthProperty()));
        fullNameLabel.textProperty().bind(Bindings.createStringBinding(() -> person.getFirstName() + " " + person.getLastName(), person.firstNameProperty(), person.lastNameProperty()));
        address1Label.textProperty().bind(person.address1Property());
        address2Label.textProperty().bind(person.address2Property());
        cityLabel.textProperty().bind(person.cityProperty());
        zipLabel.textProperty().bind(Bindings.createStringBinding(() -> person.getZip() + "", person.zipProperty()));
        initialsLabel.textProperty().bind(Bindings.createStringBinding(() -> (person.getFirstName().charAt(0) + "" + person.getLastName().charAt(0)).toUpperCase(), person.firstNameProperty(), person.lastNameProperty()));
        person.levelProperty().addListener((obs, old, val) -> {
            levelCircle.getStyleClass().clear();
            levelCircle.getStyleClass().add(val.name().toLowerCase());
        });
        levelCircle.getStyleClass().add(person.getLevel().name().toLowerCase());
    }
}
