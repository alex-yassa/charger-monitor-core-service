package pl.twerdenergoplus.charger.monitor.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MqttConfig {

    private final MqttProperties mqttProperties;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[]{mqttProperties.getBrokerUrl()});
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);

        String username = mqttProperties.getUsername();
        String password = mqttProperties.getPassword();
        if (username != null && !username.isBlank()) {
            options.setUserName(username);
        }
        if (password != null && !password.isBlank()) {
            options.setPassword(password.toCharArray());
        }

        factory.setConnectionOptions(options);
        return factory;
    }

    // ── Inbound (subscribe) ──────────────────────────────────────────────────

    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter(MqttPahoClientFactory factory) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        mqttProperties.getBrokerUrl(),
                        mqttProperties.getClientId() + "-inbound",
                        factory,
                        mqttProperties.getDefaultTopic());

        adapter.setCompletionTimeout(5_000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(mqttProperties.getQos());
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    // ── Outbound (publish) ───────────────────────────────────────────────────

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutboundHandler(MqttPahoClientFactory factory) {
        MqttPahoMessageHandler handler =
                new MqttPahoMessageHandler(
                        mqttProperties.getClientId() + "-outbound",
                        factory);

        handler.setAsync(true);
        handler.setDefaultTopic(mqttProperties.getDefaultTopic().replace("/#", ""));
        handler.setDefaultQos(mqttProperties.getQos());
        return handler;
    }
}

