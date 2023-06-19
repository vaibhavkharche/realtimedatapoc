package com.maveric.realtimedatapoc.kafka;

import com.maveric.realtimedatapoc.entity.Order;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${spring.kafka.consumer.topic.orders_data_topic}")
    private String kafkaTopic;

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    public void publishOrdersAsKafkaMessages(List<Order> orders) {
        orders.forEach(this::publishOrderAsKafkaMessage);
    }

    public void publishOrderAsKafkaMessage(Order order) {
        logger.info("publishing message to topic: {}, message: {}", kafkaTopic, order);
        ProducerRecord<String, Order> producerRecord = new ProducerRecord<>(kafkaTopic, "Key-" + ThreadLocalRandom.current().nextInt(0, 5), order);
        CompletableFuture<SendResult<String, Order>> completableFuture = kafkaTemplate.send(producerRecord);
        try {
            SendResult<String, Order> sendResult = completableFuture.get();
            producerRecord = sendResult.getProducerRecord();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            logger.info("Message Published, Details: partition: {}, offset: {}, key: {}, value: {}, timestamp: {}", recordMetadata.partition(), recordMetadata.offset(), producerRecord.key(), producerRecord.value(), producerRecord.timestamp());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            logger.error("Unable to publish message to Kafka Topic, Exception: ", e);
        }
    }
}
