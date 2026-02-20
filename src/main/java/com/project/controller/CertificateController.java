package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.CertificateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/generate/{internId}")
    public ResponseEntity<String> generateCertificate(
            @PathVariable Long internId) {

        certificateService.generateCertificate(internId);
        return ResponseEntity.ok("Certificate Generated Successfully");
    }
}
