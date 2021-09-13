package com.da.notificationconsumer.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailAccountProperties {

	@Value("${email.account.username}")
	private String username;

	@Value("${email.account.password}")
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}