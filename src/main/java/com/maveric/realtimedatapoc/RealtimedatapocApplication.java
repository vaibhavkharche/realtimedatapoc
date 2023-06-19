package com.maveric.realtimedatapoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableKafka
@EnableJpaRepositories
public class RealtimedatapocApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealtimedatapocApplication.class, args);
    }

}
