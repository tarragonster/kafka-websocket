package com.kafka.KafkaDemo.Service;

import com.kafka.KafkaDemo.Handler.CustomWebSocketHandler;
import com.kafka.KafkaDemo.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    CustomWebSocketHandler customWebSocketHandler;

    @KafkaListener(topics="Kafka_Example", groupId = "group_id")
    public void consume(String message){
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = "Kafka_Example_Json", groupId = "group_json", containerFactory = "userConcurrentKafkaListenerContainerFactory")
    public void consumerJson(User user){
        System.out.println("Consumer JSON Message: " + user);
    }

    @KafkaListener(topics = "level-pair-status", groupId = "group_socket")
    public void websocketConsumerDemo(@Payload String lvlPairStatus){
        customWebSocketHandler.sendLevelPairStatus(lvlPairStatus);
    }
}
