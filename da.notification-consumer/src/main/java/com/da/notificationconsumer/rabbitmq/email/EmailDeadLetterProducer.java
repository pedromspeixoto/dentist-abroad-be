package com.da.notificationconsumer.rabbitmq.email;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import com.da.notificationconsumer.config.ServiceProperties;
import com.da.notificationconsumer.config.rabbitmq.dlq.DeadLetterProperties;
import com.da.notificationconsumer.config.rabbitmq.email.EmailListenerConfig;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailDeadLetterProducer {

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange deadLetterExchange;
    private final ServiceProperties serviceProperties;
    private final EmailListenerConfig emailListenerProperties;
    private final DeadLetterProperties deadLetterProperties;

    @Autowired
    public EmailDeadLetterProducer(AmqpAdmin amqpAdmin,
                                 RabbitTemplate rabbitTemplate,
                                 DirectExchange deadLetterExchange,
                                 ServiceProperties serviceProperties,
                                 EmailListenerConfig emailListenerProperties,
                                 DeadLetterProperties deadLetterProperties) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.deadLetterExchange = deadLetterExchange;
        this.serviceProperties = serviceProperties;
        this.emailListenerProperties = emailListenerProperties;
        this.deadLetterProperties = deadLetterProperties;
    }

    public Queue emailDeadLetterQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", this.getQueueName());
        return new Queue(this.getQueueName(), false, false, false, args);
    }

    @PostConstruct
    public void amqpDeclarations() {
        this.amqpAdmin.declareQueue(this.emailDeadLetterQueue());
        this.amqpAdmin.declareExchange(this.deadLetterExchange);
        this.amqpAdmin.declareBinding(BindingBuilder.bind(this.emailDeadLetterQueue()).to(this.deadLetterExchange).with(this.deadLetterProperties.getEmailDeadLetterRoutingKey()));

    }

    public void send(Object message) {
        rabbitTemplate.convertAndSend(this.deadLetterExchange.getName(), this.deadLetterProperties.getEmailDeadLetterRoutingKey(), message);
    }

    public String getQueueName(){
        return this.serviceProperties.getEnvironment() + "-" + this.serviceProperties.getName() + "-"
                + this.emailListenerProperties.getQueue() + "-" + this.deadLetterProperties.getDeadLetter();
    }

}