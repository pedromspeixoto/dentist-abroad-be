package com.da.submission.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.da.submission.dto.submission.GetSubmissionResponseDto;
import com.da.submission.dto.submission.GetSubmissionsResponseDto;
import com.da.submission.dto.submission.PostSubmissionRequestDto;
import com.da.submission.dto.submission.PostSubmissionResponseDto;
import com.da.submission.exception.NotFoundException;
import com.da.submission.service.SubmissionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SubmissionController {

    private final SubmissionService submissionService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionController.class);

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    // post new submission 
    @PostMapping("/submissions")
    private ResponseEntity<?> postSubmission(HttpServletRequest httpRequest,
                                          @RequestBody PostSubmissionRequestDto submissionRequestDto) {

        PostSubmissionResponseDto submissionResponseDto;

        try {
            submissionResponseDto = submissionService.postSubmission(submissionRequestDto);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(submissionResponseDto, HttpStatus.OK);

    }

    // get submission by id 
    @GetMapping("/submissions/{submissionId}")
    public ResponseEntity<?> getSubmission(HttpServletRequest httpRequest,
                                           @PathVariable(value = "submissionId") String submissionId) {

        GetSubmissionResponseDto submissionResponseDto;

        try {
            submissionResponseDto = submissionService.getSubmissionById(submissionId);
        } catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(submissionResponseDto, HttpStatus.OK);

    }

    // get all submission 
    @GetMapping("/submissions")
    public ResponseEntity<?> getSubmissions(HttpServletRequest httpRequest,
                                            @RequestParam(value = "submissionId", required = false) Optional<String> submissionId,
                                            @RequestParam(value = "name", required = false) Optional<String> name,
                                            @RequestParam(value = "phoneNumber", required = false) Optional<String> phoneNumber,
                                            @RequestParam(value = "email", required = false) Optional<String> email,
                                            @RequestParam(value = "limit", required = false) Optional<Integer> limit,
                                            @RequestParam(value = "offset", required = false) Optional<Integer> offset) {

        GetSubmissionsResponseDto submissionsResponseDto;

        try {
            submissionsResponseDto = submissionService.getAllSubmissions(submissionId, name, phoneNumber, email, limit, offset);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(submissionsResponseDto, HttpStatus.OK);

    }
}