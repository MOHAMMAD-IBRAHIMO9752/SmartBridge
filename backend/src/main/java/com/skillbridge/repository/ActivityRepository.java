package com.skillbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillbridge.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTop5ByOrderByOccurredAtDesc();
}
