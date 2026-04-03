package pl.twerdenergoplus.charger.monitor.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Handles all inbound MQTT messages received from subscribed topics.
 * Extend this class or replace the {@code handleMqttMessage} method body
 * with your own business logic.
 */
@Slf4j
@Component
public class MqttMessageHandler {

    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public void handleMqttMessage(Message<String> message) {
        String topic   = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = message.getPayload();

        log.info("Received MQTT message | topic='{}' payload='{}'", topic, payload);

        // TODO: add your business logic here
    }
}

