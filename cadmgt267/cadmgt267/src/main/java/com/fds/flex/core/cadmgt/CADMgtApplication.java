package com.fds.flex.core.cadmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.fds.flex.core")
@EnableMongoRepositories("com.fds.flex.core.*")
@EnableMongoAuditing
@EnableCaching
@EnableScheduling
public class CADMgtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CADMgtApplication.class, args);
    }

}