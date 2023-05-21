package com.hussain.project_001.controller.utilities;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.controller.NavigationPaneController;
import com.hussain.project_001.model.RequestForm;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class RequestDetailsController extends AbstractController {
    @FXML
    private BorderPane requestDetailsPane;

    @FXML
    private Label firstNameLbl;

    @FXML
    private Label lastNameLbl;

    @FXML
    private Label emailLbl;

    @FXML
    private Label phoneLbl;

    @FXML
    private Label subjectLbl;

    @FXML
    private Label messageLbl;

    private NavigationPaneController navigationPaneController;

    private UtilitiesController utilitiesController;

    public void setUtilitiesController(final UtilitiesController utilitiesController) {
        this.utilitiesController = utilitiesController;
    }

    public RequestDetailsController() {
        super(RequestDetailsController.class.getResource("requestDetails.fxml"));
    }

    @Override
    public void onControllerLoad() {
        requestDetailsPane.setTop(getNavigationPaneController().getPanel());
    }

    public NavigationPaneController getNavigationPaneController() {
        if (navigationPaneController == null) {
            navigationPaneController = new NavigationPaneController();
            navigationPaneController.setBackHandler(utilitiesController::showUtilities);
        }
        return navigationPaneController;
    }

    public void setModel(final RequestForm requestForm) {
        firstNameLbl.setText(requestForm.getFirstName());
        lastNameLbl.setText(requestForm.getLastName());
        phoneLbl.setText(requestForm.getPhone());
        emailLbl.setText(requestForm.getEmail());
        subjectLbl.setText(requestForm.getSubject());
        messageLbl.setText(requestForm.getMessage());
    }
}
