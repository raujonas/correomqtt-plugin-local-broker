package org.correomqtt.plugin.broker.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.correomqtt.business.model.SettingsDTO;
import org.correomqtt.business.provider.SettingsProvider;
import org.correomqtt.gui.controller.SettingsViewController;
import org.correomqtt.gui.model.WindowProperty;
import org.correomqtt.gui.model.WindowType;
import org.correomqtt.gui.utils.WindowHelper;
import org.correomqtt.plugin.broker.broker.LocalBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LocalBrokerSettingsViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsViewController.class);
    private static final ResourceBundle resources = ResourceBundle.getBundle("org.correomqtt.plugin.broker.i18n", SettingsProvider.getInstance().getSettings().getCurrentLocale());
    @FXML
    private AnchorPane localBrokerSettingsPane;
    private SettingsDTO settings;
    @FXML
    private TextField portTextField;
    @FXML
    private CheckBox autoStartCheckBox;
    @FXML
    private Button startStopButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    static void showAsDialog() throws IOException {
        Map<Object, Object> properties = new HashMap<>();
        properties.put(WindowProperty.WINDOW_TYPE, WindowType.LOCAL_BROKER_SETTINGS);

        if (WindowHelper.focusWindowIfAlreadyThere(properties)) {
            return;
        }

        LocalBrokerSettingsViewController localBrokerSettingsViewController = new LocalBrokerSettingsViewController();
        FXMLLoader loader = new FXMLLoader(LocalBrokerSettingsViewController.class.getResource("/org/correomqtt/plugin/broker/controller/localBrokerSettingsView.fxml"), resources);
        loader.setController(localBrokerSettingsViewController);
        Parent parent = loader.load();

        showAsDialog((Pane) parent,
                resources.getString("localBrokerSettingsWindowTitle"),
                properties,
                event -> localBrokerSettingsViewController.onCloseDialog());
    }

    static <LocalBrokerSettingsViewController> void showAsDialog(Pane Pane, String title, Map<Object, Object> windowProperties, final EventHandler<WindowEvent> closeHandler) {

        String cssPath = SettingsProvider.getInstance().getCssPath();
        Scene scene = new Scene(Pane);
        if (cssPath != null) scene.getStylesheets().add(cssPath);
        Stage stage = new Stage();
        stage.setResizable(true);
        stage.setAlwaysOnTop(false);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        if (closeHandler != null) {
            stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, closeHandler);
        }
        stage.getScene().getWindow().getProperties().putAll(windowProperties);
    }

    private void onCloseDialog() {
        //TaskFactory.unsubscribe(getConnectionId(), subscriptionDTO);
    }

    @FXML
    public void initialize() {
        settings = SettingsProvider.getInstance().getSettings();
        setupGUI();
    }

    @FXML
    public void onStartStopClicked() {
        LOGGER.debug("Start / stop broker clicked");

        if (!LocalBroker.getInstance().isBrokerRunning()) {
            LocalBroker.getInstance().startBroker();
        } else {
            LocalBroker.getInstance().stopBroker();
        }
    }

    private void setupGUI() {
        localBrokerSettingsPane.setMinHeight(250);
        localBrokerSettingsPane.setMaxHeight(500);

        if (!LocalBroker.getInstance().isBrokerRunning()) {
            startStopButton.setText("Start");
        }

    }

    private void closeDialog() {
        Stage stage = (Stage) localBrokerSettingsPane.getScene().getWindow();
        stage.close();
    }

    private void keyHandling(KeyEvent event) {
        if (KeyCode.ESCAPE == event.getCode()) {
            closeDialog();
        }
    }

    @FXML
    public void onCancelClicked() {
        LOGGER.debug("Cancel in settings clicked");
        closeDialog();
    }

    @FXML
    public void onSaveClicked() {
        LOGGER.debug("Save in settings clicked");
        saveSettings();
        closeDialog();
    }

    private void saveSettings() {
        LOGGER.debug("Saving settings");

    }
}
