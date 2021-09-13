package com.da.submission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SubmissionMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubmissionMicroserviceApplication.class, args);
    }

}