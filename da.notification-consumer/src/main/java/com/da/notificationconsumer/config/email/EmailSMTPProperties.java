package com.da.notificationconsumer.config.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailSMTPProperties {

    @Value("${email.smtp.host}")
    private String host;

    @Value("${email.smtp.port}")
    private int port;

    @Value("${email.smtp.auth}")
    private String auth;

    @Value("${email.smtp.starttls-enable}")
    private String startTLSEnable;

    public String getHost() {
	return host;
    }

    public int getPort() {
	return port;
    }

    public String getAuth() {
	return auth;
    }

    public String getStartTLSEnable() {
	return startTLSEnable;
    }

    public Properties toProperties() {

	final Properties props = new Properties();

	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.host", this.host);
	props.put("mail.smtp.port", this.port);
	props.put("mail.smtp.auth", this.auth);
	props.put("mail.smtp.starttls.enable", this.startTLSEnable);

	return props;
    }
}