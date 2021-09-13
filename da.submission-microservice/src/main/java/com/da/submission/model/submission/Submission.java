package com.da.submission.model.submission;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.da.submission.enums.ProcessStatus;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    private String submissionId;
    private String name;
    private String nationality;
    private String phoneNumber;
    private String email;
    private String currentWorkingLocation;
    private String processStatus;
    private LocalDateTime submissionDate;
    private LocalDateTime approvalDate;
    private LocalDateTime workingDate;

    public Submission() {
    }

    public Submission(String name, String nationality, String phoneNumber, 
                      String email, String currentWorkingLocation, ProcessStatus processStatus,
                      LocalDateTime submissionDate, LocalDateTime approvalDate, LocalDateTime workingDate) {
        this.submissionId = UUID.randomUUID().toString();
        this.name = name;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.currentWorkingLocation = currentWorkingLocation;
        this.processStatus = processStatus.toString();
        this.submissionDate = submissionDate;
        this.approvalDate = approvalDate;
        this.workingDate = workingDate;
    }

    public String getSubmissionId() {
        return this.submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
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

    public String getProcessStatus() {
        return this.processStatus;
    }

    public void setProcessStatus(String processStatus) {
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