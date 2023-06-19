package com.maveric.realtimedatapoc.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.consumer.topic.orders_data_topic}")
    private String kafkaTopic;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Scheduled(fixedRate = 5000)
    public void publishMessage() throws ExecutionException, InterruptedException {
        String messageStr = "Message-" + atomicInteger.incrementAndGet();
        logger.info("publishing message to topic: {}, message: {}", kafkaTopic, messageStr);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(kafkaTopic, "Key-" + ThreadLocalRandom.current().nextInt(0, 5), messageStr);
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(producerRecord);
        SendResult<String, String> sendResult = completableFuture.get();

        producerRecord = sendResult.getProducerRecord();
        RecordMetadata recordMetadata = sendResult.getRecordMetadata();
        logger.info("Message Published, Details: ");
        logger.info("partition: {}, offset: {}, key: {}, value: {}, timestamp: {}", recordMetadata.partition(), recordMetadata.offset(), producerRecord.key(), producerRecord.value(), producerRecord.timestamp());

    }
}
