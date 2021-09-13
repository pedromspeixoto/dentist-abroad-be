package com.da.notificationconsumer.config.rabbitmq.dlq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeadLetterProperties {

    @Value("${rabbitmq.dead-letter.name}")
    private String deadLetter;

    @Value("${rabbitmq.email.dead-letter.routing-key}")
    private String emailDeadLetterRoutingKey;

    public String getEmailDeadLetterRoutingKey() {
        return emailDeadLetterRoutingKey;
    }

    public String getDeadLetter() {
        return deadLetter;
    }

}