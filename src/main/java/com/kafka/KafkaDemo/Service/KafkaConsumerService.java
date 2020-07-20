package com.kafka.KafkaDemo.Service;

import com.kafka.KafkaDemo.Model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics="Kafka_Example", groupId = "group_id")
    public void consume(String message){
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = "Kafka_Example_Json", groupId = "group_json", containerFactory = "userConcurrentKafkaListenerContainerFactory")
    public void consumerJson(User user){
        System.out.println("Consumer JSON Message: " + user);
    }
}
