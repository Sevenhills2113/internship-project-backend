package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Notification;
import com.project.entity.User;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);

    long countByUser(User user);
}
