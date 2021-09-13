package com.da.submission.service.impl;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.da.submission.dto.submission.PostSubmissionRequestDto;
import com.da.submission.dto.submission.PostSubmissionResponseDto;
import com.da.submission.dto.submission.GetSubmissionResponseDto;
import com.da.submission.dto.submission.GetSubmissionsResponseDto;
import com.da.submission.dto.submission.PutSubmissionRequestDto;
import com.da.submission.exception.NotFoundException;
import com.da.submission.model.common.PageDetails;
import com.da.submission.model.submission.Submission;
import com.da.submission.rabbitmq.EventPublisher;
import com.da.submission.repository.submission.SubmissionRepository;
import com.da.submission.service.SubmissionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Configuration
public class SubmissionServiceImpl implements SubmissionService{

    private static final Integer DEFAULT_VALUE_LIMIT = 100;
    private static final Integer DEFAULT_VALUE_OFFSET = 0;

    private static final String EMAIL_NEW_SUBMISSION = "submission-notification";
    //private static final String EMAIL_ACK_SUBMISSION = "ack-submission";

    @Value("${rabbitmq.routing-key.email-submission-notification}")
    private String EMAIL_ROUTING_KEY;

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    EventPublisher emailEventPublisher;

    @Override
    @Transactional
    public PostSubmissionResponseDto postSubmission(PostSubmissionRequestDto submissionRequestDto) {
        Submission submission = new Submission();
        ModelMapper modelMapper = new ModelMapper();
        submission = modelMapper.map(submissionRequestDto, Submission.class);
        submission.setSubmissionId(UUID.randomUUID().toString());
        submissionRepository.save(submission);
        // publish to rabbitmq to send email
        emailEventPublisher.sendEmail(EMAIL_ROUTING_KEY, EMAIL_NEW_SUBMISSION, submission.getSubmissionId(), 
                                      submission.getEmail(), submission.getPhoneNumber(), submission.getName(), 
                                      "not implemented");
        return new PostSubmissionResponseDto(submission.getSubmissionId());
    }

    @Override
    public GetSubmissionResponseDto getSubmissionById(String submissionId) throws NotFoundException {
        Optional<Submission> submission = submissionRepository.findById(submissionId);
        if(!submission.isPresent())
            throw new NotFoundException(submissionId);
        ModelMapper modelMapper = new ModelMapper();
        GetSubmissionResponseDto submissionResponseDto = new GetSubmissionResponseDto();
        submissionResponseDto = modelMapper.map(submission.get(), GetSubmissionResponseDto.class);
        return submissionResponseDto;
    }

    @Override
    public GetSubmissionsResponseDto getAllSubmissions(Optional<String> submissionId, Optional<String> name, Optional<String> phoneNumber,
                                                       Optional<String> email, Optional<Integer> limit, Optional<Integer> offset) {
        ModelMapper modelMapper = new ModelMapper();
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