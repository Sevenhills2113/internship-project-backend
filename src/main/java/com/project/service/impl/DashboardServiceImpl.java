package com.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.dto.CertificateDTO;
import com.project.dto.InternshipDTO;
import com.project.dto.NotificationDTO;
import com.project.dto.OverviewDTO;
import com.project.dto.ResourceDTO;
import com.project.dto.SubmissionDTO;
import com.project.dto.TaskResponseDTO;
import com.project.entity.Internship;
import com.project.entity.TaskStatus;
import com.project.entity.User;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.CertificateRepository;
import com.project.repository.InternshipRepository;
import com.project.repository.NotificationRepository;
import com.project.repository.ResourceRepository;
import com.project.repository.SubmissionRepository;
import com.project.repository.TaskRepository;
import com.project.repository.UserRepository;
import com.project.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final InternshipRepository internshipRepository;
    private final TaskRepository taskRepository;
    private final SubmissionRepository submissionRepository;
    private final CertificateRepository certificateRepository;
    private final NotificationRepository notificationRepository;
    private final ResourceRepository resourceRepository;

    // ================= OVERVIEW =================
    @Override
    public OverviewDTO getOverview(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );

        long active = internshipRepository.countByIntern(user);
        long completed = taskRepository.countByInternAndStatus(user, TaskStatus.COMPLETED);
        long pending = taskRepository.countByInternAndStatus(user, TaskStatus.PENDING);
        long certificates = certificateRepository.countByIntern(user);

        return OverviewDTO.builder()
                .active(active)
                .completed(completed)
                .pending(pending)
                .certificates(certificates)
                .build();
    }

    // ================= INTERNSHIPS =================
    @Override
    public List<InternshipDTO> getInternships(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        List<Internship> internships = internshipRepository.findByIntern(user);

        return internships.stream()
                .map(internship -> {

                    long totalTasks =
                            taskRepository.countByInternship(internship);

                    long completedTasks =
                            taskRepository.countByInternshipAndStatus(
                                    internship,
                                    TaskStatus.COMPLETED
                            );

                    int progress = 0;

                    if (totalTasks > 0) {
                        progress = (int) ((completedTasks * 100) / totalTasks);
                    }

                    return InternshipDTO.builder()
                            .id(internship.getId())
                            .title(internship.getTitle())
                            .company(internship.getCompany())
                            .progress(progress)
                            .build();
                })
                .collect(Collectors.toList());
    }




    // ================= TASKS =================
    @Override
    public List<TaskResponseDTO> getTasks(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );

        return taskRepository.findByIntern(user)
                .stream()
                .map(t -> TaskResponseDTO.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .deadline(t.getDeadline())
                        .status(t.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }

    // ================= SUBMISSIONS =================
    @Override
    public List<SubmissionDTO> getSubmissions(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );

        return submissionRepository.findByIntern(user)
                .stream()
                .map(s -> SubmissionDTO.builder()
                        .id(s.getId())
                        .taskName(s.getTaskName())   // ✅ correct
                        .submittedDate(s.getSubmittedDate()) // already String
                        .status(s.getStatus() != null ? s.getStatus().name() : null) // Enum → String
                        .build())
                .collect(Collectors.toList());

    }
    // ================= CERTIFICATES =================
    @Override
    public List<CertificateDTO> getCertificates(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );

        return certificateRepository.findByIntern(user)
                .stream()
                .map(c -> CertificateDTO.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .issuedDate(c.getIssuedDate())
                        .fileUrl(c.getFileUrl())
                        .build())
                .collect(Collectors.toList());
    }

    // ================= NOTIFICATIONS =================
    @Override
    public List<NotificationDTO> getNotifications(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );

        return notificationRepository.findByUser(user)
                .stream()
                .map(n -> NotificationDTO.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .message(n.getMessage())
                        .date(n.getDate())
                        .build())
                .collect(Collectors.toList());
    }

    // ================= RESOURCES =================
    @Override
    public List<ResourceDTO> getResources() {

        return resourceRepository.findAll()
                .stream()
                .map(r -> ResourceDTO.builder()
                        .id(r.getId())
                        .title(r.getTitle())
                        .link(r.getLink())
                        .build())
                .collect(Collectors.toList());
    }
}
