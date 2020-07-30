package com.kafka.KafkaDemo.Task;

import com.kafka.KafkaDemo.Model.LevelVO;
import com.kafka.KafkaDemo.Service.KafkaProducerServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DemoTask {

    private static final Logger logger = LoggerFactory.getLogger(DemoTask.class);

    @Autowired
    private KafkaProducerServiceImp kafkaService;

    @Scheduled(fixedRateString = "1500")
    private void sendPairStatusToSocket() {
        try {
            // TODO get level pair data from redis
            // Mock up data
            Random random = new Random();
            LevelVO data = new LevelVO();
            data.setLevelId(1L);
            List<String> levelName = Arrays.asList("1", "2", "3");
            data.setLevelName(levelName.get((int) (Math.random() * 2)));
            data.setBuyPrice(random.nextGaussian());
            data.setSellPrice(random.nextDouble());
            data.setMaxValue(random.nextDouble());
            data.setMinValue(random.nextDouble());
            List<String> status = Arrays.asList("Green", "Yellow", "Red");
            data.setStatus(status.get((int) (Math.random() * 2)));
            kafkaService.sendLevelPairData(data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
