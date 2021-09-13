package com.da.notificationconsumer.dto.email;

import java.io.Serializable;

import com.da.notificationconsumer.model.email.Email;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RabbitEmailEvent implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "template", required = true)
    private String templateCode;

    @JsonProperty(value = "email", required = true)
    private Email email;

    public RabbitEmailEvent(String templateCode, Email email) {
	    super();
	    this.templateCode = templateCode;
	    this.email = email;
    }

    public String getTemplateCode() {
	    return templateCode;
    }

    public void setTemplateCode(String templateCode) {
	    this.templateCode = templateCode;
    }

    public Email getEmail() {
	    return email;
    }

    public void setEmail(Email email) {
	    this.email = email;
    }

}