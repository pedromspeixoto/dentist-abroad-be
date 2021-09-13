package com.da.notificationconsumer.service;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import com.da.notificationconsumer.config.email.EmailAccountProperties;
import com.da.notificationconsumer.model.email.Email;
import com.da.notificationconsumer.service.exception.EmailValidationException;
import com.da.notificationconsumer.service.exception.TemplateValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class EmailService {


    private static final String EMAIL_NEW_SUBMISSION = "submission-notification";
    private static final String EMAIL_ACK_SUBMISSION = "ack-submission";
    private static final String[] EMAIL_TEMPLATES = { EMAIL_NEW_SUBMISSION, EMAIL_ACK_SUBMISSION };
    private static final String EMAIL_NEW_SUBMISSION_SUBJECT = "Move Dentist | Nova submissão";
    private static final String ERROR_INVALID_EMAIL_TEMPLATE = "invalid email template: ";
    private static final String ERROR_INVALID_EMAIL = "invalid email: ";
    // http://www.ex-parrot.com/pdw/Mail-RFC822-Address.html
    private static final String EMAIL_REGEX_VALIDATION = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private Session session;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private EmailAccountProperties account;

    public void sendEmail(String templateCode, Email email) throws TemplateValidationException, EmailValidationException, MessagingException {

        if (!isValidTemplate(templateCode)) {
            logger.error(ERROR_INVALID_EMAIL_TEMPLATE + templateCode);
            throw new TemplateValidationException(ERROR_INVALID_EMAIL_TEMPLATE + templateCode);
        }
    
        if (!isValidEmailAddress(email.getEmail())) {
            logger.error(ERROR_INVALID_EMAIL + email.getEmail());
            throw new EmailValidationException(email.getEmail());
        }

        final MimeMessage message = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("name", email.getName());
        String html = templateEngine.process(templateCode, context);
        helper.setText(html, true);
        helper.setFrom(account.getUsername());
        helper.setTo(email.getEmail());
        helper.addInline("logo.jpg", new ClassPathResource("img/logo.jpg"), "img/jpg");

        switch(templateCode){
            case EMAIL_NEW_SUBMISSION:
                helper.setSubject(EMAIL_NEW_SUBMISSION_SUBJECT);
                break;
            case EMAIL_ACK_SUBMISSION:
            default:
        }

        Transport.send(message);

        logger.info("email " + templateCode + " sent to " + email.getEmail());
    }

    private boolean isValidTemplate(String templateCode) {
        for (String template : EMAIL_TEMPLATES) {
            if (template.equals(templateCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidEmailAddress(String email) {
        if (null == email) {
            return false;
        }
        Pattern pattern = java.util.regex.Pattern.compile(EMAIL_REGEX_VALIDATION);
        Matcher matcher = pattern.matcher(email.toLowerCase());
        return matcher.matches();
    }
    

}