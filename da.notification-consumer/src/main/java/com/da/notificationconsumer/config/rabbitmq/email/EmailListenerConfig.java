package com.da.notificationconsumer.config.rabbitmq.email;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class EmailListenerConfig {

    @Value("${spring.application.name}")
    private String name;

    @Value("${spring.profiles.active}")
    private String environment;

    @Value("${rabbitmq.email.queue}")
    private String queue;

    @Value("${rabbitmq.email.durable}")
    private Boolean durable;

    @Value("${rabbitmq.email.routing-keys}")
    private List<String> routingKeys;

    private final AmqpAdmin amqpAdmin;
    public ConnectionFactory connectionFactory;
    public DirectExchange exchange;
    public MessageConverter messageConverter;
    private final RetryOperationsInterceptor emailDeadLetterInterceptor;

    public EmailListenerConfig(AmqpAdmin amqpAdmin,
                             ConnectionFactory connectionFactory, DirectExchange exchange, MessageConverter messageConverter,
                             @Qualifier("emailDeadLetterInterceptor") RetryOperationsInterceptor emailDeadLetterInterceptor) {

        this.amqpAdmin = amqpAdmin;
        this.connectionFactory = connectionFactory;
        this.exchange = exchange;
        this.messageConverter = messageConverter;
        this.emailDeadLetterInterceptor = emailDeadLetterInterceptor;
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(environment + "-" + name + "-" + queue, durable);
    }

    @Bean
    public List<Binding> emailQueueBindings() {
        List<Binding> bindings = new ArrayList<Binding>();
        for (String routingKey : routingKeys) {
            bindings.add(BindingBuilder.bind(this.emailQueue()).to(this.exchange).with(routingKey));
        }
        return bindings;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory emailListenerFactory() {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(this.connectionFactory);
        containerFactory.setMaxConcurrentConsumers(10);
        containerFactory.setConcurrentConsumers(1);
        containerFactory.setAutoStartup(true);
        containerFactory.setMessageConverter(this.messageConverter);
        containerFactory.setPrefetchCount(10);
        // containerFactory.setDefaultRequeueRejected(true);
        containerFactory.setAdviceChain(emailDeadLetterInterceptor);
        return containerFactory;
    }

    @PostConstruct
    public void amqpDeclarations() {
        this.amqpAdmin.declareQueue(this.emailQueue());
        this.amqpAdmin.declareExchange(this.exchange);
        for (Binding binding : this.emailQueueBindings()) {
            this.amqpAdmin.declareBinding(binding);
        }
    }

    public String getQueue() {
        return this.queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

}