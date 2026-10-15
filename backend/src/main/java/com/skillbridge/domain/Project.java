package com.skillbridge.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1200)
    private String description;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(nullable = false)
    private Instant createdAt;

    protected Project() {
    }

    public Project(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
