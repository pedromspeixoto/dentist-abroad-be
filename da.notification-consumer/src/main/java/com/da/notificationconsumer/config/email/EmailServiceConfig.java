package com.da.notificationconsumer.config.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailServiceConfig {

    @Autowired
    private EmailSMTPProperties smtpProperties;

    @Autowired
    private EmailAccountProperties account;

    @Bean
    public Session session() {
	return Session.getInstance(smtpProperties.toProperties(), new Authenticator() {

	    @Override
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(account.getUsername(), account.getPassword());
	    }

	});
    }
}