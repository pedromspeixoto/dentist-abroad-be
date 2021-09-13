package com.da.notificationconsumer.model.email;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Email {

    @JsonProperty(value = "submission_id", required = false)
    private String submissionId;

    @JsonProperty(value = "email", required = false)
    private String email;

    @JsonProperty(value = "phoneNumber", required = false)
    private String phoneNumber;

    @JsonProperty(value = "name", required = false)
    private String name;

    @JsonProperty(value = "notes", required = false)
    private String notes;

    @JsonProperty(value = "properties", required = false)
    private Map<String, Object> properties;

    public Email() {

    }

    public String getSubmissionId() {
        return this.submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

}