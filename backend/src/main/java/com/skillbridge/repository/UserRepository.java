package com.skillbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillbridge.domain.Role;
import com.skillbridge.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    long countByRole(Role role);
    List<User> findTop5ByOrderByCreatedAtDesc();
}
