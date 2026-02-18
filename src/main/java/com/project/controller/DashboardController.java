package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.CertificateDTO;
import com.project.dto.InternshipDTO;
import com.project.dto.NotificationDTO;
import com.project.dto.OverviewDTO;
import com.project.dto.ResourceDTO;
import com.project.dto.SubmissionDTO;
import com.project.dto.TaskResponseDTO;
import com.project.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5181")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview/{userId}")
    public ResponseEntity<OverviewDTO> getOverview(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getOverview(userId)
        );
    }

    @GetMapping("/internships/{userId}")
    public ResponseEntity<List<InternshipDTO>> getInternships(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getInternships(userId)
        );
    }

    @GetMapping("/tasks/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getTasks(userId)
        );
    }

    @GetMapping("/submissions/{userId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissions(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getSubmissions(userId)
        );
    }

    @GetMapping("/certificates/{userId}")
    public ResponseEntity<List<CertificateDTO>> getCertificates(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getCertificates(userId)
        );
    }

    @GetMapping("/notifications/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getNotifications(userId)
        );
    }

    @GetMapping("/resources")
    public ResponseEntity<List<ResourceDTO>> getResources() {

        return ResponseEntity.ok(
                dashboardService.getResources()
        );
    }
}
