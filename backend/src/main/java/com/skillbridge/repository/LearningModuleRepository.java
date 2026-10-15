package com.skillbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillbridge.domain.LearningModule;

public interface LearningModuleRepository extends JpaRepository<LearningModule, Long> {
}
