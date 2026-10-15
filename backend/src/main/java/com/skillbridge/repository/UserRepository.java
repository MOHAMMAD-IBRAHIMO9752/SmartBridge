package com.skillbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillbridge.domain.Role;
import com.skillbridge.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    long countByRole(Role role);
    java.util.Optional<User> findByEmailIgnoreCase(String email);
    List<User> findTop5ByOrderByCreatedAtDesc();
}
