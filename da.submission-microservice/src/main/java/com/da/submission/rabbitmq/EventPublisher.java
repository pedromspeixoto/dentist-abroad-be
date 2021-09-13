package com.da.submission.rabbitmq;

import com.da.submission.dto.email.RabbitEmailEvent;
import com.da.submission.dto.email.RabbitEmailModel;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    @Autowired
    private AmqpTemplate communicationExchangeConnector;

    // send order to rabbitmq broker (by default to top priority)
    public void sendEmail(String routingKey, String templateCode, String submissionId,
                          String email, String phoneNumber, String name, String notes) {
        RabbitEmailEvent object = new RabbitEmailEvent();
        RabbitEmailModel emailObject = new RabbitEmailModel();
        emailObject.setSubmissionId(submissionId);
        emailObject.setEmail(email);
        emailObject.setPhoneNumber(phoneNumber);
        emailObject.setName(name);
        emailObject.setNotes(notes);
        object.setTemplateCode(templateCode);
        object.setEmail(emailObject);
        try {
            communicationExchangeConnector.convertAndSend(routingKey, object);
        } catch (AmqpException e) {
            throw e;
        }
    }

}