package pl.twerdenergoplus.charger.monitor.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Service for publishing Kafka messages.
 * Use {@link #publish(String, String)} to send a message to a specific topic,
 * or {@link #publish(String)} to send to the default topic configured in application.properties.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    /**
     * Publish a message to the given Kafka topic.
     *
     * @param topic   destination topic
     * @param payload message payload (String)
     */
    public void publish(String topic, String payload) {
        log.debug("Publishing Kafka message to topic '{}': {}", topic, payload);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, payload);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish Kafka message to topic '{}': {}", topic, ex.getMessage(), ex);
            } else {
                log.debug("Published Kafka message to topic='{}' partition={} offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }

    /**
     * Publish a message with a key to the given Kafka topic.
     *
     * @param topic   destination topic
     * @param key     message key
     * @param payload message payload (String)
     */
    public void publish(String topic, String key, String payload) {
        log.debug("Publishing Kafka message to topic '{}' with key '{}': {}", topic, key, payload);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, payload);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish Kafka message to topic '{}': {}", topic, ex.getMessage(), ex);
            } else {
                log.debug("Published Kafka message to topic='{}' partition={} offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }

    /**
     * Publish a message to the default topic configured in application.properties.
     *
     * @param payload message payload (String)
     */
    public void publish(String payload) {
        publish(kafkaProperties.getDefaultTopic(), payload);
    }
}

