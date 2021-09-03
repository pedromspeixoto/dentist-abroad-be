package com.sa.submission.dto.submission;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sa.submission.model.common.PageDetails;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GetSubmissionsResponseDto {

    private List<GetSubmissionResponseDto> submissionsList;
    private PageDetails pageDetails;

    public GetSubmissionsResponseDto() {
    }

    public GetSubmissionsResponseDto(List<GetSubmissionResponseDto> submissionsList, PageDetails pageDetails) {
        this.submissionsList = submissionsList;
        this.pageDetails = pageDetails;
    }

    public List<GetSubmissionResponseDto> getSubmissionsList() {
        return this.submissionsList;
    }

    public void setSubmissionsList(List<GetSubmissionResponseDto> submissionsList) {
        this.submissionsList = submissionsList;
    }

    public PageDetails getPageDetails() {
        return this.pageDetails;
    }

    public void setPageDetails(PageDetails pageDetails) {
        this.pageDetails = pageDetails;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}