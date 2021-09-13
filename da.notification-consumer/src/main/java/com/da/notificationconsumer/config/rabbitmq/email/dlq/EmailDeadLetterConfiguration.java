package com.da.notificationconsumer.config.rabbitmq.email.dlq;

import com.da.notificationconsumer.config.rabbitmq.dlq.DeadLetterProperties;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class EmailDeadLetterConfiguration {

    private final DirectExchange deadLetterExchange;
    private final DeadLetterProperties deadLetterProperties;
    private final RabbitTemplate rabbitTemplate;
    private final ExponentialBackOffPolicy exponentialBackOffPolicy;

    @Autowired
    public EmailDeadLetterConfiguration(DirectExchange deadLetterExchange,
                                      DeadLetterProperties deadLetterProperties,
                                      RabbitTemplate rabbitTemplate,
                                      ExponentialBackOffPolicy exponentialBackOffPolicy ) {
        this.deadLetterExchange = deadLetterExchange;
        this.deadLetterProperties = deadLetterProperties;
        this.rabbitTemplate = rabbitTemplate;
        this.exponentialBackOffPolicy = exponentialBackOffPolicy;
    }

    @Bean
    @Qualifier("emailDeadLetterInterceptor")
    public RetryOperationsInterceptor emailDeadLetterInterceptor() {
        return RetryInterceptorBuilder.stateless().maxAttempts(5).recoverer(
                new RepublishMessageRecoverer(this.rabbitTemplate, this.deadLetterExchange.getName(), 
                                              this.deadLetterProperties.getEmailDeadLetterRoutingKey()))
                .backOffPolicy(this.exponentialBackOffPolicy).build();
    }

}