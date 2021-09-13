package com.da.notificationconsumer.service.exception;


public class EmailValidationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_PREFIX = "invalid email: ";

    public EmailValidationException(String email) {
	super(DEFAULT_MESSAGE_PREFIX + email);
    }

    public EmailValidationException(String email, Throwable cause) {
	super(DEFAULT_MESSAGE_PREFIX + email, cause);
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

}