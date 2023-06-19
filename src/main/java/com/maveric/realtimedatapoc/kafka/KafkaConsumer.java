package com.maveric.realtimedatapoc.kafka;

import com.maveric.realtimedatapoc.entity.Order;
import com.maveric.realtimedatapoc.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.consumer.topic.orders_data_topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Order order) {
        logger.info("Received message: {}", order);
        try {
            orderService.saveOrder(order);
        } catch (Exception e) {
            logger.error("Enable to save order, Exception: ", e);
        }

    }

}