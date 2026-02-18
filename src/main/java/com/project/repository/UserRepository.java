package com.project.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Role;
import com.project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (for register validation)
    Optional<User> findByEmail(String email);

    // Find user for login
    Optional<User> findByEmailAndPasswordAndRole(
            String email,
            String password,
            Role role
    );

    // Optional: count users by role
    long countByRole(Role role);
}

