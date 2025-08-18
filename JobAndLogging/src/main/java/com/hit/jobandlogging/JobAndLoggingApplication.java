package com.hit.jobandlogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JobAndLoggingApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobAndLoggingApplication.class, args);
    }

}
