package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.project.entity.Internship;
import com.project.entity.Task;
import com.project.entity.TaskStatus;
import com.project.entity.User;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Intern based
    List<Task> findByIntern(User intern);

    long countByIntern(User intern);

    long countByInternAndStatus(User intern, TaskStatus status);

    // 🔥 Internship based (for progress calculation)
    long countByInternship(Internship internship);

    long countByInternshipAndStatus(Internship internship, TaskStatus status);

    // Update task status
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status WHERE t.title = :title")
    void updateTaskStatusByTitle(String title, TaskStatus status);
}
