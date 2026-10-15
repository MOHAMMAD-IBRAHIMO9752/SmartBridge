package com.skillbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillbridge.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
