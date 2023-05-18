package com.hussain.project_001.controller;

import com.hussain.project_001.model.Person;
import com.hussain.project_001.services.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    private PersonService personService;

    private Map<Person, PersonInfoTileController> tileControllerMap = new HashMap<>();

    private List<Person> persons;

    /**
     * Constructor.
     */
    public PersonsListController() {
        super(PersonsListController.class.getResource("personsList.fxml"));
    }

    @Override
    public void onControllerLoad() {
        LOGGER.info("Loaded PersonsListController");
        /* It's up to you how to inject a service. */
        personService = new PersonService();
        initSearchField();
        persons = personService.getPersons();
        loadPersons(persons);
    }

    private void loadPersons(List<Person> persons) {
        flowPane.getChildren().clear();
        for (final Person person : persons) {
            final PersonInfoTileController infoController = tileControllerMap.computeIfAbsent(person, person1 -> new PersonInfoTileController(person1));
            flowPane.getChildren().add(infoController.getPanel());
        }
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
            filterPersons();
        });

        searchField.setOnAction(e -> filterPersons());

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

    private void filterPersons() {
        String search = searchField.getText();
        if (search != null && !search.isEmpty()) {
            String txt = search.toLowerCase();
            List<Person> filteredPersons = persons.stream().filter(p -> p.getFirstName().toLowerCase().contains(txt) || p.getLastName().toLowerCase().contains(txt)).collect(Collectors.toList());
            loadPersons(filteredPersons);
        } else {
            loadPersons(persons);
        }
    }
}
