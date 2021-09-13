package com.da.notificationconsumer.rabbitmq.email;

import javax.mail.MessagingException;

import com.da.notificationconsumer.dto.email.RabbitEmailEvent;
import com.da.notificationconsumer.service.EmailService;
import com.da.notificationconsumer.service.exception.EmailValidationException;
import com.da.notificationconsumer.service.exception.TemplateValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @Autowired
    private EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @RabbitListener(queues = { "#{emailQueue.name}" }, containerFactory = "emailListenerFactory")
    public void handleEvent(final RabbitEmailEvent event, final Message message) throws InterruptedException {

        MessageProperties properties = message.getMessageProperties();

        logger.info("event:" + properties.getReceivedRoutingKey() + " message_id:" + properties.getMessageId()
            + " correlation_id:" + properties.getCorrelationId() + " redelivered:" + properties.getRedelivered());

        try {
            emailService.sendEmail(event.getTemplateCode(), event.getEmail());
        } catch (TemplateValidationException | EmailValidationException e) {
            throw new AmqpRejectAndDontRequeueException(e);
        } catch (MessagingException e) {
            throw new ListenerExecutionFailedException(e.getMessage(), e, message);
        }
    }

}