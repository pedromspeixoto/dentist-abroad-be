package com.da.submission.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RabbitEmailModel {
    
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

    public RabbitEmailModel() {

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

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}