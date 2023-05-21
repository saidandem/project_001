package com.hussain.project_001.controller.utilities;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.controller.SearchPaneController;
import com.hussain.project_001.controller.dashboard.DashboardController;
import com.hussain.project_001.model.RequestForm;
import com.hussain.project_001.model.Utility;
import com.hussain.project_001.services.UtilityService;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UtilitiesController extends AbstractController {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UtilitiesController.class.getName());

    @FXML
    private BorderPane utilitiesPane;

    @FXML
    private BorderPane utilitiesListPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowPane;

    @FXML
    private SearchPaneController searchPaneController;

    private UtilityService utilityService;

    private Map<Utility, UtilityInfoTileController> tileControllerMap = new HashMap<>();

    private List<Utility> utilities;

    private DashboardController dashboardController;

    private RequestFormController requestFormController;

    private RequestDetailsController requestDetailsController;

    /**
     * Constructor.
     */
    public UtilitiesController() {
        super(UtilitiesController.class.getResource("utilities.fxml"));
    }

    @Override
    public void onControllerLoad() {
        LOGGER.info("Loaded UtilitiesListController");
        /* It's up to you how to inject a service. */
        utilityService = new UtilityService();
        searchPaneController.setSearchHandler(this::filterUtilities);
        searchPaneController.setPromptText("Title");
        utilities = utilityService.getUtilities();
        loadUtilities(utilities);
    }

    private void loadUtilities(List<Utility> utilities) {
        flowPane.getChildren().clear();
        for (final Utility utility : utilities) {
            final UtilityInfoTileController infoController = tileControllerMap.computeIfAbsent(utility, u1 -> new UtilityInfoTileController(u1));
            infoController.getPanel().setOnMouseClicked(e -> editUtility(utility));
            flowPane.getChildren().add(infoController.getPanel());
        }
    }

    private void editUtility(final Utility utility) {
        utilitiesPane.setCenter(getRequestFormController().getPanel());
        getRequestFormController().setModel(utility);
    }

    public void showUtilities(){
        utilitiesPane.setCenter(utilitiesListPane);
    }

    private void filterUtilities(String search) {
        if (search != null && !search.isEmpty()) {
            String txt = search.toLowerCase();
            List<Utility> filteredUtilities = utilities.stream().filter(p -> p.getTitle().toLowerCase().contains(txt)).collect(Collectors.toList());
            loadUtilities(filteredUtilities);
        } else {
            loadUtilities(utilities);
        }
    }

    public void setDashboardController(final DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    private RequestFormController getRequestFormController() {
        if (requestFormController == null) {
            requestFormController = new RequestFormController();
            requestFormController.setUtilitiesController(this);
        }
        return requestFormController;
    }

    public RequestDetailsController getRequestDetailsController() {
        if (requestDetailsController == null) {
            requestDetailsController = new RequestDetailsController();
            requestDetailsController.setUtilitiesController(this);
        }
        return requestDetailsController;
    }

    public void showRequestDetails(RequestForm requestForm){
        utilitiesPane.setCenter(getRequestDetailsController().getPanel());
        getRequestDetailsController().setModel(requestForm);
    }
}
