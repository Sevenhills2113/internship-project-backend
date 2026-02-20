package com.project.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.TaskCreateDTO;
import com.project.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mentor")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(
            @Valid @RequestBody TaskCreateDTO dto) {

        taskService.createTask(dto);

        return ResponseEntity.ok("Task Assigned Successfully");
    }
}
