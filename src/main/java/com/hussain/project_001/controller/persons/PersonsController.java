package com.hussain.project_001.controller.persons;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.controller.SearchPaneController;
import com.hussain.project_001.controller.dashboard.DashboardController;
import com.hussain.project_001.model.Person;
import com.hussain.project_001.services.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PersonsController extends AbstractController {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    @FXML
    private BorderPane personsPane;

    @FXML
    private BorderPane personsListPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowPane;

    @FXML
    private SearchPaneController searchPaneController;

    private PersonService personService;

    private Map<Person, PersonInfoTileController> tileControllerMap = new HashMap<>();

    private List<Person> persons;

    private DashboardController dashboardController;

    private PersonEditController personEditController;
    /**
     * Constructor.
     */
    public PersonsController() {
        super(PersonsController.class.getResource("persons.fxml"));
    }

    @Override
    public void onControllerLoad() {
        LOGGER.info("Loaded PersonsListController");
        /* It's up to you how to inject a service. */
        personService = new PersonService();
        searchPaneController.setSearchHandler(this::filterPersons);
        searchPaneController.setPromptText("First Name or Last Name");
        persons = personService.getPersons();
        loadPersons(persons);
    }

    private void loadPersons(List<Person> persons) {
        flowPane.getChildren().clear();
        for (final Person person : persons) {
            final PersonInfoTileController infoController = tileControllerMap.computeIfAbsent(person, person1 -> new PersonInfoTileController(person1));
            infoController.getPanel().setOnMouseClicked(e -> editPerson(person));
            flowPane.getChildren().add(infoController.getPanel());
        }
    }

    private void editPerson(final Person person) {
        personsPane.setCenter(getPersonEditController().getPanel());
        getPersonEditController().setModel(person);
    }

    public void showPersons(){
        personsPane.setCenter(personsListPane);
    }

    private void filterPersons(String search) {
        if (search != null && !search.isEmpty()) {
            String txt = search.toLowerCase();
            List<Person> filteredPersons = persons.stream().filter(p -> p.getFirstName().toLowerCase().contains(txt) || p.getLastName().toLowerCase().contains(txt)).collect(Collectors.toList());
            loadPersons(filteredPersons);
        } else {
            loadPersons(persons);
        }
    }

    public void setDashboardController(final DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public PersonEditController getPersonEditController() {
        if(personEditController==null){
            personEditController = new PersonEditController();
            personEditController.setPersonsController(this);
        }
        return personEditController;
    }
}
