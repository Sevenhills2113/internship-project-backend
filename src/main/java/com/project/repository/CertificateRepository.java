package com.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Certificate;
import com.project.entity.User;

public interface CertificateRepository
        extends JpaRepository<Certificate, Long> {

    List<Certificate> findByIntern(User intern);

    long countByIntern(User intern);
    
    boolean existsByIntern(User intern);

}
