package pl.twerdenergoplus.charger.monitor.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String bootstrapServers = "localhost:9092";
    private String groupId = "monitor-group";
    private String clientId = "monitor-client";
    private String defaultTopic = "monitor";
    private String autoOffsetReset = "earliest";
    private boolean enableAutoCommit = true;
}

