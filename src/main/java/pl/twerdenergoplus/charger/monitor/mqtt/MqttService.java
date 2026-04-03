package pl.twerdenergoplus.charger.monitor.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * Service for publishing MQTT messages.
 * Use {@link MqttPublisher} (the inner gateway) to send messages to a specific topic.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService {

    private final MqttPublisher mqttPublisher;

    /**
     * Publish a message to the given MQTT topic.
     *
     * @param topic   destination topic
     * @param payload message payload (String)
     */
    public void publish(String topic, String payload) {
        log.debug("Publishing MQTT message to topic '{}': {}", topic, payload);
        mqttPublisher.sendToMqtt(payload, topic);
    }

    /**
     * Publish a message to the default topic configured in application.properties.
     *
     * @param payload message payload (String)
     */
    public void publish(String payload) {
        log.debug("Publishing MQTT message to default topic: {}", payload);
        mqttPublisher.sendToMqtt(payload);
    }

    // ── Outbound gateway ─────────────────────────────────────────────────────

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttPublisher {

        void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);

        void sendToMqtt(String payload);
    }
}

