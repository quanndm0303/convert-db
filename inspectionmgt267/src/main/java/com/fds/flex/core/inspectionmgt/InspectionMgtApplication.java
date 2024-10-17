package com.fds.flex.core.inspectionmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.fds.flex.core")
@EnableMongoRepositories("com.fds.flex.core.*")
@EnableMongoAuditing
@EnableCaching
@EnableScheduling
public class InspectionMgtApplication {
    public static void main(String[] args) {
        SpringApplication.run(InspectionMgtApplication.class, args);
    }
}