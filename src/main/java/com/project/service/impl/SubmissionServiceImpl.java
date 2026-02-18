package com.project.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.dto.SubmissionCreateDTO;
import com.project.dto.SubmissionDTO;
import com.project.entity.Certificate;
import com.project.entity.Submission;
import com.project.entity.SubmissionStatus;
import com.project.entity.TaskStatus;
import com.project.entity.User;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.CertificateRepository;
import com.project.repository.SubmissionRepository;
import com.project.repository.TaskRepository;
import com.project.repository.UserRepository;
import com.project.service.SubmissionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CertificateRepository certificateRepository;



    @Override
    public void submitTask(SubmissionCreateDTO dto) {

        User intern = userRepository.findById(dto.getInternId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Intern not found")
                );

        Submission submission = Submission.builder()
                .taskName(dto.getTaskName())
                .submittedDate(LocalDate.now().toString())
                .status(SubmissionStatus.PENDING)
                .intern(intern)
                .build();

        submissionRepository.save(submission);
    }

    @Override
    public void approveSubmission(Long submissionId) {

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Submission not found")
                );

        submission.setStatus(SubmissionStatus.APPROVED);
        
        taskRepository.updateTaskStatusByTitle(
                submission.getTaskName(),
                TaskStatus.COMPLETED
        );
        // 3️⃣ Generate certificate automatically
        Certificate certificate = Certificate.builder()
                .title("Certificate for " + submission.getTaskName())
                .issuedDate(LocalDate.now().toString())
                .fileUrl("generated-certificate.pdf") // later dynamic
                .intern(submission.getIntern())
                .build();

        certificateRepository.save(certificate);
    }

    @Override
    public void rejectSubmission(Long submissionId) {

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Submission not found")
                );

        submission.setStatus(SubmissionStatus.REJECTED);
    }

    @Override
    public List<SubmissionDTO> getSubmissionsByIntern(Long internId) {

        User intern = userRepository.findById(internId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Intern not found")
                );

        return submissionRepository.findByIntern(intern)
                .stream()
                .map(s -> SubmissionDTO.builder()
                        .id(s.getId())
                        .taskName(s.getTaskName())
                        .submittedDate(s.getSubmittedDate())
                        .status(s.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<SubmissionDTO> getPendingSubmissions() {

        return submissionRepository.findByStatus(SubmissionStatus.PENDING)
                .stream()
                .map(s -> SubmissionDTO.builder()
                        .id(s.getId())
                        .taskName(s.getTaskName())
                        .submittedDate(s.getSubmittedDate())
                        .status(s.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }
    
}
