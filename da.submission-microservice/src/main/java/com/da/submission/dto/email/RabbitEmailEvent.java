package com.da.submission.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RabbitEmailEvent {

    @JsonProperty("template")
    private String templateCode;

    @JsonProperty("email")
    private RabbitEmailModel email;

    @JsonProperty("email")
    public RabbitEmailModel getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(RabbitEmailModel email) {
        this.email = email;
    }

    @JsonProperty("template")
    public String getTemplateCode() {
        return this.templateCode;
    }

    @JsonProperty("template")
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}