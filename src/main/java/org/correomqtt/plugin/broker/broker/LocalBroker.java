package org.correomqtt.plugin.broker.broker;

import com.hivemq.embedded.EmbeddedHiveMQ;
import com.hivemq.embedded.EmbeddedHiveMQBuilder;
import org.correomqtt.business.provider.SettingsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalBroker {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsProvider.class);
    private static LocalBroker instance = null;
    private EmbeddedHiveMQBuilder embeddedHiveMQBuilder = null;
    private boolean isBrokerRunning = false;

    private LocalBroker() {
    }

    public static synchronized LocalBroker getInstance() {
        if (instance == null) {
            instance = new LocalBroker();
        }
        return instance;
    }

    public void startBroker(){
        embeddedHiveMQBuilder = EmbeddedHiveMQ.builder();

        try (final EmbeddedHiveMQ hiveMQ = embeddedHiveMQBuilder.build()) {
            hiveMQ.start().join();
            isBrokerRunning = true;
            LOGGER.debug("Embedded broker started");
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopBroker(){
        try (final EmbeddedHiveMQ hiveMQ = embeddedHiveMQBuilder.build()) {
            hiveMQ.stop().join();
            isBrokerRunning = false;
            LOGGER.debug("Embedded broker stopped");
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isBrokerRunning() {
        return isBrokerRunning;
    }
}
