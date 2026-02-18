package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.TaskResponseDTO;
import com.project.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5181")
public class InternTaskController {

    private final TaskService taskService;

    @GetMapping("/{internId}")
    public ResponseEntity<List<TaskResponseDTO>> getInternTasks(
            @PathVariable Long internId) {

        return ResponseEntity.ok(
                taskService.getTasksByIntern(internId)
        );
    }
}

