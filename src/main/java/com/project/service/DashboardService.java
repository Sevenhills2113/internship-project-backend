package com.project.service;


import java.util.List;

import com.project.dto.CertificateDTO;
import com.project.dto.InternshipDTO;
import com.project.dto.NotificationDTO;
import com.project.dto.OverviewDTO;
import com.project.dto.ResourceDTO;
import com.project.dto.SubmissionDTO;
import com.project.dto.TaskResponseDTO;



public interface DashboardService {

    OverviewDTO getOverview(Long userId);

    List<InternshipDTO> getInternships(Long userId);

    List<TaskResponseDTO> getTasks(Long userId);
    
    List<SubmissionDTO> getSubmissions(Long userId);

    List<CertificateDTO> getCertificates(Long userId);

    List<NotificationDTO> getNotifications(Long userId);

    List<ResourceDTO> getResources();
}
