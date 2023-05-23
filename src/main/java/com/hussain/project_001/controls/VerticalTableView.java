package com.hussain.project_001.controls;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class VerticalTableView extends GridPane {
    private List<String> titles;
    private List<String> values;

    public VerticalTableView(List<String> titles, List<String> values) {
        this.titles = titles;
        this.values = values;
        getStyleClass().add("vertical-table-view");
    }

    public VerticalTableView() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    @Override
    protected void layoutChildren() {
        if (getChildren().isEmpty()) {
            if (titles.size() != values.size()) {
                return;
            }
            for (int row = 0; row < titles.size(); row++) {
                int index = row;
                String evenOddStyleClass = index % 2 == 0 ? "even" : "odd";
                final Label titleLabel = new Label(titles.get(index));
                titleLabel.getStyleClass().add("title-label");
                final StackPane titleColumn = new StackPane(titleLabel);
                titleColumn.getStyleClass().addAll("title-column", evenOddStyleClass);

                final Label valueLabel = new Label(values.get(index));
                valueLabel.getStyleClass().add("value-label");
                valueLabel.textProperty().addListener((obs, old, val) -> values.set(index, val));

                final TextField textField = new TextField();
                textField.visibleProperty().bind(valueLabel.visibleProperty().not());
                textField.textProperty().bindBidirectional(valueLabel.textProperty());
                textField.focusedProperty().addListener((obs, old, focused) -> {
                    if (!focused) {
                        valueLabel.setVisible(true);
                    }
                });
                textField.setOnAction(e -> valueLabel.setVisible(true));

                final StackPane valueColumn = new StackPane(valueLabel, textField);
                valueColumn.getStyleClass().addAll("value-column", evenOddStyleClass);
                valueColumn.setOnMouseClicked(e -> {
                    if (valueLabel.isVisible()) {
                        valueLabel.setVisible(false);
                        textField.requestFocus();
                    }
                });

                addRow(index, titleColumn, valueColumn);
            }
        }
        super.layoutChildren();
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(final List<String> titles) {
        this.titles = titles;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(final List<String> values) {
        this.values = values;
    }
}
