package com.skillbridge.web;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Profile;

import com.skillbridge.domain.Activity;
import com.skillbridge.domain.Role;
import com.skillbridge.repository.ActivityRepository;
import com.skillbridge.repository.LearningModuleRepository;
import com.skillbridge.repository.ProjectRepository;
import com.skillbridge.repository.SkillRepository;
import com.skillbridge.repository.UserRepository;

@RestController
@RequestMapping("/api/dashboard")
@Profile("!test")
public class DashboardController {
    private final UserRepository users;
    private final SkillRepository skills;
    private final ProjectRepository projects;
    private final LearningModuleRepository modules;
    private final ActivityRepository activities;

    public DashboardController(UserRepository users, SkillRepository skills, ProjectRepository projects,
            LearningModuleRepository modules, ActivityRepository activities) {
        this.users = users;
        this.skills = skills;
        this.projects = projects;
        this.modules = modules;
        this.activities = activities;
    }

    @GetMapping
    public DashboardResponse dashboard() {
        return new DashboardResponse(
                users.countByRole(Role.STUDENT),
                projects.countByStatus("RECRUITING"),
                modules.count(),
                skills.count(),
                activities.findTop5ByOrderByOccurredAtDesc().stream().map(ActivityItem::from).toList());
    }

    public record DashboardResponse(long students, long recruitingProjects, long learningModules,
            long skills, List<ActivityItem> activities) {
    }

    public record ActivityItem(String type, String title, String detail, String accent, Instant occurredAt) {
        static ActivityItem from(Activity activity) {
            return new ActivityItem(activity.getType(), activity.getTitle(), activity.getDetail(),
                    activity.getAccent(), activity.getOccurredAt());
        }
    }
}
