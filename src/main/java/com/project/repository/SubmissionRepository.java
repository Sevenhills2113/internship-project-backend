package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Submission;
import com.project.entity.SubmissionStatus;
import com.project.entity.User;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByIntern(User intern);

    List<Submission> findByStatus(SubmissionStatus status);
}
