package com.sa.submission.repository.submission;

import java.util.Optional;

import com.sa.submission.model.submission.Submission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {

	@Query(
		value = "" +
			"SELECT * " +
			"FROM submissions" +
			"WHERE COALESCE(submission_id, '') LIKE CONCAT('%', LOWER(:submission_id), '%') " +
			"AND COALESCE(name, '') LIKE CONCAT('%', LOWER(:name), '%') " +
			"AND COALESCE(phone_number, '') LIKE CONCAT('%', UPPER(:phone_number), '%') " +
			"AND COALESCE(email, '') LIKE CONCAT('%', UPPER(:email), '%')",
		nativeQuery = true
		)
	public Page<Submission> getAllSubmissions(@Param("submission_id") String submissionId,
											  @Param("name") String name,
											  @Param("phone_number") String phoneNumber,
											  @Param("email") String email,
											  Pageable pageable);

	public Optional<Submission> gindBySubmissionId(String submissionId);

}