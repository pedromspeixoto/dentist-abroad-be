package com.sa.submission.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import com.sa.submission.dto.submission.PostSubmissionRequestDto;
import com.sa.submission.dto.submission.PostSubmissionResponseDto;
import com.sa.submission.dto.submission.GetSubmissionResponseDto;
import com.sa.submission.dto.submission.GetSubmissionsResponseDto;
import com.sa.submission.dto.submission.PutSubmissionRequestDto;
import com.sa.submission.exception.NotFoundException;
import com.sa.submission.model.common.PageDetails;
import com.sa.submission.model.submission.Submission;
import com.sa.submission.repository.submission.SubmissionRepository;
import com.sa.submission.service.SubmissionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubmissionServiceImpl implements SubmissionService{

    private static final Integer DEFAULT_VALUE_LIMIT = 100;
    private static final Integer DEFAULT_VALUE_OFFSET = 0;

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public PostSubmissionResponseDto postSubmission(PostSubmissionRequestDto submissionRequestDto) {
        Submission submission = new Submission();
        submission = modelMapper.map(submission, Submission.class);
        submissionRepository.save(submission);
        return new PostSubmissionResponseDto(submission.getSubmissionId());
    }

    @Override
    public GetSubmissionResponseDto getSubmissionById(String submissionId) throws NotFoundException {
        Optional<Submission> submission = submissionRepository.findById(submissionId);
        if(!submission.isPresent())
            throw new NotFoundException(submissionId);
        GetSubmissionResponseDto submissionResponseDto = new GetSubmissionResponseDto();
        submissionResponseDto = modelMapper.map(submission.get(), GetSubmissionResponseDto.class);
        return submissionResponseDto;
    }

    @Override
    public GetSubmissionsResponseDto getAllSubmissions(Optional<String> submissionId, Optional<String> name, Optional<String> phoneNumber,
                                                       Optional<String> email, Optional<Integer> limit, Optional<Integer> offset) {
        Pageable pageable = PageRequest.of(offset.orElse(DEFAULT_VALUE_OFFSET), limit.orElse(DEFAULT_VALUE_LIMIT));
        Page<Submission> submissionsPage = submissionRepository.getAllSubmissions(submissionId.orElse(""),
                                                                                  name.orElse(""),
                                                                                  phoneNumber.orElse(""),
                                                                                  email.orElse(""),
                                                                                  pageable);
        GetSubmissionsResponseDto submissionsResponseDto = new GetSubmissionsResponseDto(submissionsPage.getContent().stream()
                                                                                                      .map(submission -> modelMapper.map(submission, GetSubmissionResponseDto.class))
                                                                                                      .collect(Collectors.toList()),
                                                                                         new PageDetails(limit.orElse(DEFAULT_VALUE_LIMIT),
                                                                                                         offset.orElse(DEFAULT_VALUE_OFFSET),
                                                                                                         submissionsPage.getTotalElements()));
        return submissionsResponseDto;
    }

    @Override
    @Transactional
    public void updateSubmission(PutSubmissionRequestDto submissionRequestDto) {
        // TODO - not implemented
    }

    @Override
    @Transactional
    public void deleteSubmission(String submissionId) {
        // TODO - not implemented
    }

}