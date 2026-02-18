package com.project.service;

import java.util.List;

import com.project.dto.TaskCreateDTO;
import com.project.dto.TaskResponseDTO;

public interface TaskService {

    void createTask(TaskCreateDTO dto);
    
    List<TaskResponseDTO> getTasksByIntern(Long internId);
}

