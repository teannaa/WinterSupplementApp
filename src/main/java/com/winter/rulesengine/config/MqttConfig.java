package com.winter.rulesengine.config;


import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@IntegrationComponentScan
public class MqttConfig {

    /**
     * Explanation:
     *
     * Client Factory: Configures the MQTT connection.
     * Input Channel: A channel to receive messages.
     * Inbound Adapter: Subscribes to MQTT topics and routes messages to the input channel.
     * Outbound Handler: Publishes messages to MQTT topics.
     *
     */

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://test.mosquitto.org"});
        options.setCleanSession(true);
     // Set callback to monitor connection events
        options.setConnectionTimeout(10); // Set connection timeout in seconds
        options.setKeepAliveInterval(20); // Set keep alive interval in second
        
        
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @Primary
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter inbound() {
        String topic = "BRE/calculateWinterSupplementInput/#";
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("rules-engine-client", mqttClientFactory(), topic);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler("rules-engine-client", mqttClientFactory());
        messageHandler.setAsync(true);
        return messageHandler;
    }
}
