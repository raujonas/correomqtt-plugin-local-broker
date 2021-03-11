package org.correomqtt.plugin.broker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.correomqtt.business.provider.SettingsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;

public class LocalBrokerMenuItemController {

    private Logger LOGGER = LoggerFactory.getLogger(LocalBrokerMenuItemController.class);

    @FXML
    private MenuItem localBrokerMenuItem;

    public LocalBrokerMenuItemController() {
    }

    public void addItems(Menu menu, int i) {
        menu.getItems().add(i, localBrokerMenuItem);
    }

    @FXML
    void onClicked() throws IOException {
        LOGGER.info("localBrokerMenuItem clicked");
        LocalBrokerSettingsViewController.showAsDialog();
    }
}




