package com.hussain.project_001.controller.utilities;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.controller.NavigationPaneController;
import com.hussain.project_001.model.RequestForm;
import com.hussain.project_001.model.Utility;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class RequestFormController extends AbstractController {

    @FXML
    private BorderPane utilityEditPane;

    @FXML
    private GridPane utilityFormLayout;

    @FXML
    private Label formTitleLabel;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    @FXML
    private TextArea message;

    @FXML
    private RadioButton projectEnquiry;

    @FXML
    private RadioButton feedBack;

    @FXML
    private RadioButton others;

    @FXML
    private Button sendBtn;

    @FXML
    private ProgressBar submitProgress;

    private Utility utility;

    private NavigationPaneController navigationPaneController;

    private UtilitiesController utilitiesController;

    public RequestFormController() {
        super(RequestFormController.class.getResource("requestForm.fxml"));
    }

    private RequestForm requestForm;

    @Override
    public void onControllerLoad() {
        utilityEditPane.setTop(getNavigationPaneController().getPanel());
        sendBtn.setOnAction(this::submitForm);
    }

    private void submitForm(final ActionEvent actionEvent) {
        submitProgress.setProgress(0);
        submitProgress.setVisible(true);
        utilityFormLayout.setDisable(true);
        KeyValue keyValue = new KeyValue(submitProgress.progressProperty(), 1.0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), e -> showSuccessPage(), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    private void bindModel(){
        firstName.textProperty().bindBidirectional(requestForm.firstNameProperty());
        lastName.textProperty().bindBidirectional(requestForm.lastNameProperty());
        email.textProperty().bindBidirectional(requestForm.emailProperty());
        phone.textProperty().bindBidirectional(requestForm.phoneProperty());
        message.textProperty().bindBidirectional(requestForm.messageProperty());
        projectEnquiry.setSelected(true);
    }

    private void unbindModel(){
        firstName.textProperty().unbind();
        lastName.textProperty().unbind();
        email.textProperty().unbind();
        phone.textProperty().unbind();
        message.textProperty().unbind();
    }

    private String getSubject() {
        if (projectEnquiry.isSelected()) {
            return "Project enquiry";
        } else if (feedBack.isSelected()) {
            return "Feedback";
        } else {
            return "Others";
        }
    }

    private void showSuccessPage() {
        utilityFormLayout.setDisable(false);
        submitProgress.setVisible(false);
        utilitiesController.showRequestDetails(requestForm);
    }

    public void setModel(Utility u) {
        unbindModel();
        this.utility = u;
        formTitleLabel.setText(this.utility.getTitle() + " Request");
        requestForm = new RequestForm();
        bindModel();
    }

    public void setUtilitiesController(final UtilitiesController utilitiesController) {
        this.utilitiesController = utilitiesController;
    }

    public NavigationPaneController getNavigationPaneController() {
        if (navigationPaneController == null) {
            navigationPaneController = new NavigationPaneController();
            navigationPaneController.setBackHandler(utilitiesController::showUtilities);
        }
        return navigationPaneController;
    }
}
