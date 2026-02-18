package com.project.service;



import com.project.dto.SubmissionCreateDTO;
import com.project.dto.SubmissionDTO;

import java.util.List;

public interface SubmissionService {

    void submitTask(SubmissionCreateDTO dto);

    void approveSubmission(Long submissionId);

    void rejectSubmission(Long submissionId);

    List<SubmissionDTO> getSubmissionsByIntern(Long internId);
    
    List<SubmissionDTO> getPendingSubmissions();
}
