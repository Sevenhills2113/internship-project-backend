package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Internship;
import com.project.entity.User;

public interface InternshipRepository
        extends JpaRepository<Internship, Long> {

    // Get internships for an intern
    List<Internship> findByIntern(User intern);

    // Count internships for overview
    long countByIntern(User intern);
}
