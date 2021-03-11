package org.correomqtt.plugin.broker;

import javafx.fxml.FXMLLoader;
import javafx.util.FXPermission;
import org.correomqtt.business.provider.SettingsProvider;
import org.correomqtt.plugin.manager.PermissionPlugin;
import org.pf4j.PluginRuntimeException;
import org.pf4j.PluginWrapper;
import java.io.FilePermission;
import java.io.IOException;
import java.security.Permissions;
import java.util.PropertyPermission;
import java.util.ResourceBundle;

public class LocalBrokerPlugin extends PermissionPlugin {

    private static final ResourceBundle resources = ResourceBundle.getBundle("org.correomqtt.plugin.broker.i18n", SettingsProvider.getInstance().getSettings().getCurrentLocale());

    public LocalBrokerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public Permissions getPermissions() {
        Permissions permissions = new Permissions();
        permissions.add(new FXPermission("accessWindowList"));
        permissions.add(new FXPermission("accessClipboard"));
        permissions.add(new FilePermission("<<ALL FILES>>", "read,write"));
        permissions.add(new PropertyPermission("*", "read"));
        return permissions;
    }

     static void loadFXML(String resourceName, Object controller) {
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(resourceName), resources);
        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException e) {
            throw new PluginRuntimeException("Failed to load layout file");
        }
    }
}
