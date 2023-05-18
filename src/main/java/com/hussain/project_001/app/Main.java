package com.hussain.project_001.app;

import com.hussain.project_001.controller.DashboardController;
import com.hussain.project_001.utils.MyScenicView;
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
        Scene scene = new Scene(dashboardController.getPanel(), 900, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/base.css").toExternalForm());
        stage.setTitle("Project 001");
        stage.setScene(scene);
        stage.show();
        MyScenicView.show(scene);
    }

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
