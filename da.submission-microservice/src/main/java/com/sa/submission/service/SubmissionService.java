package com.sa.submission.service;

import com.sa.submission.exception.NotFoundException;

import java.util.Optional;

import com.sa.submission.dto.submission.GetSubmissionResponseDto;
import com.sa.submission.dto.submission.GetSubmissionsResponseDto;
import com.sa.submission.dto.submission.PostSubmissionRequestDto;
import com.sa.submission.dto.submission.PostSubmissionResponseDto;
import com.sa.submission.dto.submission.PutSubmissionRequestDto;

public interface SubmissionService {

    // post new submission
    PostSubmissionResponseDto postSubmission(PostSubmissionRequestDto submissionRequestDto);

    // get submission by id
    GetSubmissionResponseDto getSubmissionById(String submissionId) throws NotFoundException;

    // get all submissions
    GetSubmissionsResponseDto getAllSubmissions(Optional<String> submissionId, Optional<String> name, Optional<String> phoneNumber,
                                                Optional<String> email, Optional<Integer> limit, Optional<Integer> offset);

    // update submission
    void updateSubmission(PutSubmissionRequestDto submissionRequestDto);

    // delete submission
    void deleteSubmission(String submissionId);

}