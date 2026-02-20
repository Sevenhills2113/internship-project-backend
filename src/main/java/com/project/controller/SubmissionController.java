package com.project.controller;

import com.project.dto.SubmissionCreateDTO;
import com.project.dto.SubmissionDTO;
import com.project.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SubmissionController {

    private final SubmissionService submissionService;

    // Intern submits
    @PostMapping
    public ResponseEntity<String> submitTask(
            @Valid @RequestBody SubmissionCreateDTO dto) {

        submissionService.submitTask(dto);
        return ResponseEntity.ok("Task Submitted Successfully");
    }

    // Mentor approves
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveSubmission(
            @PathVariable Long id) {

        submissionService.approveSubmission(id);
        return ResponseEntity.ok("Submission Approved");
    }

    // Mentor rejects
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectSubmission(
            @PathVariable Long id) {

        submissionService.rejectSubmission(id);
        return ResponseEntity.ok("Submission Rejected");
    }

    // Intern view
    @GetMapping("/intern/{internId}")
    public ResponseEntity<List<SubmissionDTO>> getInternSubmissions(
            @PathVariable Long internId) {

        return ResponseEntity.ok(
                submissionService.getSubmissionsByIntern(internId)
        );
    }

    // Mentor view pending
    @GetMapping("/pending")
    public ResponseEntity<List<SubmissionDTO>> getPendingSubmissions() {

        return ResponseEntity.ok(
                submissionService.getPendingSubmissions()
        );
    }
}
