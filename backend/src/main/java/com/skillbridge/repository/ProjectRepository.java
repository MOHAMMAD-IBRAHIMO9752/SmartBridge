package com.skillbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillbridge.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    long countByStatus(String status);
}
