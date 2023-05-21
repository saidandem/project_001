package com.hussain.project_001.controller.utilities;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.model.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class UtilityInfoTileController extends AbstractController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private StackPane iconPane;

    private Utility utility;

    public UtilityInfoTileController(Utility utility) {
        super(UtilityInfoTileController.class.getResource("utilityInfoTile.fxml"));
        this.utility = utility;
    }

    @Override
    public void onControllerLoad() {
        bindModel();
    }

    private void bindModel() {
        titleLabel.textProperty().bind(utility.titleProperty());
        descriptionLabel.textProperty().bind(utility.descriptionProperty());
        iconPane.getStyleClass().add(utility.getStyle());
    }
}
