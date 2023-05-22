package com.hussain.project_001.app;

import com.hussain.project_001.controller.dashboard.DashboardController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the application.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        DashboardController dashboardController = new DashboardController();
        Scene scene = new Scene(dashboardController.getPanel(), 820, 620);
        scene.getStylesheets().addAll(getClass().getResource("/styles/base.css").toExternalForm(),
                getClass().getResource("/styles/app.css").toExternalForm());
        stage.setTitle("Project 001");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
