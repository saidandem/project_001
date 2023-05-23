package com.hussain.project_001.controller.settings;

import com.hussain.project_001.controller.AbstractController;
import com.hussain.project_001.controls.VerticalTableView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SettingsController extends AbstractController {

    @FXML
    private GridPane settingsPane;

    @FXML
    private VerticalTableView type1TableView;

    @FXML
    private VerticalTableView type2TableView;

    @FXML
    private VerticalTableView type3TableView;

    @FXML
    private VerticalTableView type4TableView;

    public SettingsController() {
        super(SettingsController.class.getResource("settings.fxml"));
    }

    @Override
    public void onControllerLoad() {
        // Type 1
        List<String> type1Data = List.of("Years", "Months", "Days", "Years1", "Months1", "Days1");
        List<String> type1DataValue = List.of("", "", "", "", "", "");
        type1TableView.setTitles(new ArrayList<>(type1Data));
        type1TableView.setValues(new ArrayList<>(type1DataValue));

        // Type 2
        List<String> type2Data = List.of("Name", "Age", "DOB");
        List<String> type2DataValue = List.of("John", "32", "01/01/1990");
        type2TableView.getStyleClass().add("type2");
        type2TableView.setTitles(new ArrayList<>(type2Data));
        type2TableView.setValues(new ArrayList<>(type2DataValue));

        // Type 3
        List<String> type3Data = List.of("Group 1", "Group 2", "Group 3", "Group 4");
        List<String> type3DataValue = List.of("Yellow", "", "Red", "");
        type3TableView.getStyleClass().add("type3");
        type3TableView.setTitles(new ArrayList<>(type3Data));
        type3TableView.setValues(new ArrayList<>(type3DataValue));

        // Type 4
        List<String> type4Data = List.of("East", "West", "North", "South");
        List<String> type4DataValue = List.of("90deg", "120deg", "100deg", "12deg");
        type4TableView.getStyleClass().add("type4");
        type4TableView.setTitles(new ArrayList<>(type4Data));
        type4TableView.setValues(new ArrayList<>(type4DataValue));
    }
}
