package com.skillbridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "learning_modules")
public class LearningModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 40)
    private String category;

    @Column(nullable = false)
    private Integer durationMinutes;

    protected LearningModule() {
    }

    public LearningModule(String title, String category, Integer durationMinutes) {
        this.title = title;
        this.category = category;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public Integer getDurationMinutes() { return durationMinutes; }
}
