package com.winter.rulesengine.mqtt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.winter.rulesengine.model.WinterSupplementInput;
import com.winter.rulesengine.model.WinterSupplementOutput;
import com.winter.rulesengine.service.RulesEngineService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/** handleIncomingMessage listens for MQTT messages, 
 * converts them to WinterSupplementInput, processes them, 
 * and sends the result with the ID to the output topic. */

@Service
public class MqttService {

    @Autowired
    private RulesEngineService rulesEngineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageChannel mqttOutboundChannel;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter inboundAdapter;

    @Value("${mqtt.topic.input.prefix}")
    private String inputTopicPrefix;

    @Value("${mqtt.topic.output.prefix}")
    private String outputTopicPrefix;
    
    
    private final Set<String> currentSubscriptions = new HashSet<>();


    /**
     * Dynamically update the subscription to a specific input topic.
     * @param topicId Unique topic ID from the web app.
     */
    public void updateSubscription(String topicId) {
        try {
            if (inboundAdapter == null) {
                throw new IllegalStateException("InboundAdapter is not initialized");
            }

            String newTopic = inputTopicPrefix + topicId;

            // Only update if the new topic isn't already subscribed
            if (!currentSubscriptions.contains(newTopic)) {
                inboundAdapter.stop();

                // Remove old subscriptions
                for (String oldTopic : new HashSet<>(currentSubscriptions)) {
                    inboundAdapter.removeTopic(oldTopic);
                    currentSubscriptions.remove(oldTopic);
                }

                // Add new subscription
                inboundAdapter.addTopic(newTopic);
                currentSubscriptions.add(newTopic);

                inboundAdapter.start();

                System.out.println("Subscription updated successfully: " + newTopic);
            } else {
                System.out.println("Already subscribed to topic: " + newTopic);
            }
        } catch (Exception e) {
            System.out.println("Error updating subscription: " + e.getMessage());
        }
    }
    @Async
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleIncomingMessage(String payload) {
        try {
            // Deserialize the input payload
            WinterSupplementInput input = objectMapper.readValue(payload, WinterSupplementInput.class);

            // Process the input using business logic asynchronously
            Future<WinterSupplementOutput> outputFuture = rulesEngineService.processInputAsync(input);
            WinterSupplementOutput output = outputFuture.get(30, TimeUnit.SECONDS);  // Timeout to avoid indefinite waiting

            // Serialize the output to JSON
            String outputJson = objectMapper.writeValueAsString(output);

            // Dynamically generate the output topic using the input ID
            String outputTopic = outputTopicPrefix + input.getId();

            // Ensure message is published only once
            synchronized (this) {
                boolean published = mqttOutboundChannel.send(
                    MessageBuilder.withPayload(outputJson)
                        .setHeader("mqtt_topic", outputTopic)
                        .build()
                );
                
                if (published) {
                    System.out.println("Message successfully published to topic:!!! " + outputTopic);
                } else {
                    System.out.println("Failed to publish message to topic: " + outputTopic);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Set<String> getCurrentSubscriptions() {
        return new HashSet<>(currentSubscriptions);
    }

}
