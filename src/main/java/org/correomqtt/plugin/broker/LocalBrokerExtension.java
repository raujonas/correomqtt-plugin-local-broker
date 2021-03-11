package org.correomqtt.plugin.broker;

import javafx.scene.control.Menu;
import org.correomqtt.business.provider.SettingsProvider;
import org.correomqtt.plugin.broker.controller.LocalBrokerMenuItemController;
import org.correomqtt.plugin.spi.ToolsMenuHook;
import org.pf4j.Extension;

import java.util.ResourceBundle;

@Extension
public class LocalBrokerExtension implements ToolsMenuHook {

    private static final ResourceBundle resources = ResourceBundle.getBundle("org.correomqtt.plugin.broker.i18n", SettingsProvider.getInstance().getSettings().getCurrentLocale());

    @Override
    public void onInstantiateToolsMenu(Menu menu, int i) {
        LocalBrokerMenuItemController controller = new LocalBrokerMenuItemController();
        LocalBrokerPlugin.loadFXML("/org/correomqtt/plugin/broker/controller/localBrokerMenuItem.fxml", controller);
        controller.addItems(menu, i);
    }
}
