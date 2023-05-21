package com.hussain.project_001.controller.persons;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.controller.NavigationPaneController;
import com.hussain.project_001.model.Level;
import com.hussain.project_001.model.Person;
import com.hussain.project_001.services.MockPersonsDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class PersonEditController extends AbstractController {

    @FXML
    private BorderPane personEditPane;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private CheckBox zone1CB;

    @FXML
    private CheckBox zone2CB;

    @FXML
    private CheckBox zone3CB;

    @FXML
    private CheckBox notificationsCB;

    @FXML
    private ChoiceBox<Level> statusChoiceBox;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private Slider scoreSlider;

    @FXML
    private Button saveBtn;

    private NavigationPaneController navigationPaneController;

    private Person person;

    private PersonsController personsController;

    public PersonEditController() {
        super(PersonEditController.class.getResource("personEdit.fxml"));
    }

    @Override
    public void onControllerLoad() {
        personEditPane.setTop(getNavigationPaneController().getPanel());
        statusChoiceBox.getItems().addAll(Level.values());
        cityComboBox.getItems().addAll(MockPersonsDB.CITIES);
        saveBtn.setOnAction(e->savePerson());
    }

    private void savePerson(){
        this.person.setFirstName(firstName.getText());
        this.person.setLastName(lastName.getText());
        this.person.setGender(maleRadio.isSelected()?"Male":"Female");
        this.person.setDateOfBirth(dateOfBirth.getValue());
        this.person.setSendNotifications(notificationsCB.isSelected());
        this.person.setLevel(statusChoiceBox.getValue());
        this.person.setCity(cityComboBox.getValue());
        this.person.setScore(scoreSlider.getValue());
        personsController.showPersons();

    }
    public NavigationPaneController getNavigationPaneController() {
        if (navigationPaneController == null) {
            navigationPaneController = new NavigationPaneController();
            navigationPaneController.setBackHandler(personsController::showPersons);
        }
        return navigationPaneController;
    }

    public void setModel(Person p) {
        this.person = p;
        firstName.setText(this.person.getFirstName());
        lastName.setText(this.person.getLastName());
        maleRadio.setSelected(this.person.getGender().equalsIgnoreCase("male"));
        femaleRadio.setSelected(this.person.getGender().equalsIgnoreCase("female"));
        dateOfBirth.setValue(this.person.getDateOfBirth());
        notificationsCB.setSelected(this.person.isSendNotifications());
        cityComboBox.setValue(this.person.getCity());
        statusChoiceBox.setValue(this.person.getLevel());
        scoreSlider.setValue(this.person.getScore());
    }

    public void setPersonsController(final PersonsController personsController) {
        this.personsController = personsController;
    }
}
