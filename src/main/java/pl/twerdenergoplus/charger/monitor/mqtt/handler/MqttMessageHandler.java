package pl.twerdenergoplus.charger.monitor.mqtt.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import pl.twerdenergoplus.charger.monitor.service.InfluxService;

/**
 * Handles all inbound MQTT messages received from subscribed topics.
 * Extend this class or replace the {@code handleMqttMessage} method body
 * with your own business logic.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttMessageHandler {

    private final InfluxService influxService;

    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public void handleMqttMessage(Message<String> message) {
        String topic   = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
        String payload = message.getPayload();

        log.info("Received MQTT message | topic='{}' payload='{}'", topic, payload);

        if (topic == null) {
            return;
        }

        String[] topicParts = topic.split("/");
        String[] payloadParts = payload.split(":");

        if (topicParts.length == 4 && payloadParts.length == 3) {

            try {
                influxService.writeDeviceData(
                        topicParts[1], // deviceId
                        topicParts[2], // shmFileName
                        topicParts[3], // index
                        Long.parseLong(payloadParts[0]), // timestamp
                        Integer.parseInt(payloadParts[2]) // data
                );
            } catch (Exception e) {
                log.info("Fail to save: " + e.getMessage());
            }
        }
    }
}

