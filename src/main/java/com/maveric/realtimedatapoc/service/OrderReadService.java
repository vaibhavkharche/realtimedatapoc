package com.maveric.realtimedatapoc.service;

import com.maveric.realtimedatapoc.entity.Order;
import com.maveric.realtimedatapoc.kafka.KafkaProducer;
import com.maveric.realtimedatapoc.util.CsvReaderUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class OrderReadService {

    private static final Logger logger = LoggerFactory.getLogger(OrderReadService.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostConstruct
    public void init() {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler(localExecutor);
        concurrentTaskScheduler.schedule(() -> {
            try {
                List<Order> orders = CsvReaderUtil.readOrderFromCsv();
                kafkaProducer.publishOrdersAsKafkaMessages(orders);
            } catch (IOException e) {
                Thread.currentThread().interrupt();
                logger.error("Unable to read csv file, Exception: ", e);
            }
        }, new Date(System.currentTimeMillis() + 10000));
    }
}
