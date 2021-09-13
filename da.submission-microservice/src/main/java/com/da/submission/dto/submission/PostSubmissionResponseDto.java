package com.da.submission.dto.submission;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PostSubmissionResponseDto {
    
    private String submissionId;

    public PostSubmissionResponseDto() {
    }

    public PostSubmissionResponseDto(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getSubmissionId() {
        return this.submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}