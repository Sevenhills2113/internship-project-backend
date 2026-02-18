package com.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.dto.TaskCreateDTO;
import com.project.dto.TaskResponseDTO;
import com.project.entity.Task;
import com.project.entity.TaskStatus;
import com.project.entity.User;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.TaskRepository;
import com.project.repository.UserRepository;
import com.project.service.TaskService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public void createTask(TaskCreateDTO dto) {

        User intern = userRepository.findById(dto.getInternId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Intern not found")
                );

        User mentor = userRepository.findById(dto.getMentorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mentor not found")
                );

        Task task = Task.builder()
                .title(dto.getTitle())
                .deadline(dto.getDeadline())
                .status(TaskStatus.PENDING)
                .intern(intern)
                .mentor(mentor)
                .build();

        taskRepository.save(task);
    }

    @Override
    public List<TaskResponseDTO> getTasksByIntern(Long internId) {

        User intern = userRepository.findById(internId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Intern not found")
                );

        return taskRepository.findByIntern(intern)
                .stream()
                .map(t -> TaskResponseDTO.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .deadline(t.getDeadline())
                        .status(t.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }
}
