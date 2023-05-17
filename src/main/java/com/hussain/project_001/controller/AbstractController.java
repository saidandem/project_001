package com.hussain.project_001.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;

/**
 * Implementation for the abstract controller.
 */
public abstract class AbstractController {

    /** FXML URL. */
    private final URL fxmlURL;

    /** Root panel of the controller. */
    private Parent panel;

    public AbstractController(final URL url){
        this.fxmlURL = url;
    }

    /**
     * Returns the controller root node. When called the first time, this method invokes {@link #makePanel()} to build
     * the FX components of the controller.
     *
     * @return the panel object
     */
    public final Parent getPanel() {
        if (panel == null) {
            makePanel();
        }
        return panel;
    }

    /**
     * Action to execute when a controller is loaded.
     */
    public abstract void onControllerLoad();

    /**
     * Implementation to load the FXML file using the URL passed to {@link AbstractController}.
     */
    private void makePanel() {
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(fxmlURL);
        try {
            final Region pane = loader.load();
            onControllerLoad();
            setPanel(pane);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Failed to load " + fxmlURL.getFile(), e);
        }
    }

    /**
     * Set the root of this panel controller.
     *
     * @param aPanel the root panel (non null).
     */
    private void setPanel(final Parent aPanel) {
        assert aPanel != null;
        panel = aPanel;
    }
}
