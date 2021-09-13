package com.da.submission.dto.submission;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.da.submission.enums.ProcessStatus;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class PostSubmissionRequestDto {

    private String name;
    private String nationality;
    private String phoneNumber;
    private String email;
    private String currentWorkingLocation;
    private ProcessStatus processStatus;
    private LocalDateTime submissionDate;
    private LocalDateTime approvalDate;
    private LocalDateTime workingDate;

    public PostSubmissionRequestDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentWorkingLocation() {
        return this.currentWorkingLocation;
    }

    public void setCurrentWorkingLocation(String currentWorkingLocation) {
        this.currentWorkingLocation = currentWorkingLocation;
    }

    public ProcessStatus getProcessStatus() {
        return this.processStatus;
    }

    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    public LocalDateTime getSubmissionDate() {
        return this.submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDateTime getApprovalDate() {
        return this.approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDateTime getWorkingDate() {
        return this.workingDate;
    }

    public void setWorkingDate(LocalDateTime workingDate) {
        this.workingDate = workingDate;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}