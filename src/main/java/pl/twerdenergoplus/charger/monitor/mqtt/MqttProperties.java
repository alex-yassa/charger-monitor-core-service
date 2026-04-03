package pl.twerdenergoplus.charger.monitor.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    private String brokerUrl = "tcp://localhost:1883";
    private String clientId = "monitor-client";
    private String username = "";
    private String password = "";
    private String defaultTopic = "monitor/#";
    private int qos = 1;
    private int keepAliveInterval = 60;
    private int connectionTimeout = 30;
}

