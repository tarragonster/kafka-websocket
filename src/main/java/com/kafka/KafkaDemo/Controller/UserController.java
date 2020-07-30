package com.kafka.KafkaDemo.Controller;

import com.kafka.KafkaDemo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class UserController {

    @Autowired
    KafkaTemplate<String, User> kafkaUserTemplate;

    private static final String TOPIC = "Kafka_Example_Json";

    @GetMapping("/publish/{message}")
    public String post(@PathVariable("message") final String message){

        kafkaUserTemplate.send(TOPIC, new User(message, "IT","3000F"));

        return "published successfully";
    }

    @PostMapping("/publish/{message}")
    public String producing(@PathVariable("message") final String message){

        kafkaUserTemplate.send(TOPIC, new User(message, "IT","3000F"));

        return "published successfully";
    }
}
