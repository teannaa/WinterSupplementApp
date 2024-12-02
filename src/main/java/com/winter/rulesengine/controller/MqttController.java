package com.winter.rulesengine.controller;

import com.winter.rulesengine.mqtt.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    @Autowired
    private MqttService mqttService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToTopic(@RequestParam String topicId) {
        mqttService.updateSubscription(topicId);
        return ResponseEntity.ok("Subscribed to topic: " + topicId);
    }
}
