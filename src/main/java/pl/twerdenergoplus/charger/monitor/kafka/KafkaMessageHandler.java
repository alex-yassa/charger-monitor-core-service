package pl.twerdenergoplus.charger.monitor.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Handles all inbound Kafka messages received from subscribed topics.
 * Extend this class or replace the {@code handleKafkaMessage} method body
 * with your own business logic.
 */
@Slf4j
@Component
public class KafkaMessageHandler {

    @KafkaListener(
            topics = "${kafka.default-topic}",
            groupId = "${kafka.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleKafkaMessage(ConsumerRecord<String, String> record) {
        log.info("Received Kafka message | topic='{}' partition={} offset={} key='{}' value='{}'",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());

        // TODO: add your business logic here
    }
}

