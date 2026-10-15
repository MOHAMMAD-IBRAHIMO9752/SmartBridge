package com.skillbridge.web;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Profile;

import com.skillbridge.domain.LearningModule;
import com.skillbridge.domain.Project;
import com.skillbridge.domain.Skill;
import com.skillbridge.domain.User;
import com.skillbridge.repository.LearningModuleRepository;
import com.skillbridge.repository.ProjectRepository;
import com.skillbridge.repository.SkillRepository;
import com.skillbridge.repository.UserRepository;

@RestController
@RequestMapping("/api")
@Profile("!test")
public class PageDataController {
    private final UserRepository users;
    private final ProjectRepository projects;
    private final SkillRepository skills;
    private final LearningModuleRepository modules;

    public PageDataController(UserRepository users, ProjectRepository projects, SkillRepository skills,
            LearningModuleRepository modules) {
        this.users = users;
        this.projects = projects;
        this.skills = skills;
        this.modules = modules;
    }

    @GetMapping("/users")
    public List<UserSummary> users() {
        return users.findAll().stream().map(user -> new UserSummary(user.getId(), user.getName(),
                user.getEmail(), user.getRole())).toList();
    }

    @GetMapping("/projects")
    public List<Project> projects() {
        return projects.findAll();
    }

    @GetMapping("/skills")
    public List<Skill> skills() {
        return skills.findAll();
    }

    @GetMapping("/learning/modules")
    public List<LearningModule> modules() {
        return modules.findAll();
    }

    public record UserSummary(Long id, String name, String email, com.skillbridge.domain.Role role) {
    }
}
