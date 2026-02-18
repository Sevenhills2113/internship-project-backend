package com.project.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Resource;

public interface ResourceRepository
        extends JpaRepository<Resource, Long> {
}
