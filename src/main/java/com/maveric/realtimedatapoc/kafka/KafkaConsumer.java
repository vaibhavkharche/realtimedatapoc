package com.maveric.realtimedatapoc.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @KafkaListener(topics = "${spring.kafka.consumer.topic.orders_data_topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        logger.info("Received message: {}", message);
    }

}