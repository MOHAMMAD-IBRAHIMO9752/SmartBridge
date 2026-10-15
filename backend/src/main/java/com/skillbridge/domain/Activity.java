package com.skillbridge.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false, length = 20)
    private String accent;

    @Column(nullable = false)
    private Instant occurredAt;

    protected Activity() {
    }

    public Activity(String type, String title, String detail, String accent, Instant occurredAt) {
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.accent = accent;
        this.occurredAt = occurredAt;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getDetail() { return detail; }
    public String getAccent() { return accent; }
    public Instant getOccurredAt() { return occurredAt; }
}
