package com.skillbridge.seed;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.skillbridge.domain.Activity;
import com.skillbridge.domain.LearningModule;
import com.skillbridge.domain.Project;
import com.skillbridge.domain.Role;
import com.skillbridge.domain.Skill;
import com.skillbridge.domain.User;
import com.skillbridge.repository.ActivityRepository;
import com.skillbridge.repository.LearningModuleRepository;
import com.skillbridge.repository.ProjectRepository;
import com.skillbridge.repository.SkillRepository;
import com.skillbridge.repository.UserRepository;

@Configuration
@Profile("!test")
public class SeedDataConfiguration {

    @Bean
    CommandLineRunner seedData(
            UserRepository users,
            SkillRepository skills,
            ProjectRepository projects,
            LearningModuleRepository modules,
            ActivityRepository activities,
            org.springframework.core.env.Environment environment) {
        return args -> {
            if (!Boolean.parseBoolean(environment.getProperty("skillbridge.seed-data-enabled", "true"))
                    || users.count() > 0) {
                return;
            }

            users.saveAll(List.of(
                    new User("John Doe", "john.doe@skillbridge.local", Role.STUDENT),
                    new User("Aarav Sharma", "aarav.sharma@skillbridge.local", Role.STUDENT),
                    new User("Maya Khan", "maya.khan@skillbridge.local", Role.STUDENT),
                    new User("Rohan Farooq", "rohan.farooq@skillbridge.local", Role.STUDENT),
                    new User("Ananya Patel", "ananya.patel@skillbridge.local", Role.STUDENT),
                    new User("Ishaan Mehta", "ishaan.mehta@skillbridge.local", Role.STUDENT),
                    new User("Sara Thomas", "sara.thomas@skillbridge.local", Role.STUDENT),
                    new User("Vikram Nair", "vikram.nair@skillbridge.local", Role.STUDENT),
                    new User("Nisha Verma", "nisha.verma@skillbridge.local", Role.STUDENT),
                    new User("Kabir Singh", "kabir.singh@skillbridge.local", Role.STUDENT),
                    new User("Diya Iyer", "diya.iyer@skillbridge.local", Role.STUDENT),
                    new User("Arjun Rao", "arjun.rao@skillbridge.local", Role.STUDENT),
                    new User("Meera Joshi", "meera.joshi@skillbridge.local", Role.STUDENT),
                    new User("Zoya Ali", "zoya.ali@skillbridge.local", Role.STUDENT),
                    new User("Dev Malhotra", "dev.malhotra@skillbridge.local", Role.STUDENT),
                    new User("Priya Menon", "priya.menon@skillbridge.local", Role.STUDENT),
                    new User("SkillBridge Admin", "admin@skillbridge.local", Role.ADMIN)));

            skills.saveAll(List.of(
                    new Skill("Java", "Backend"), new Skill("Spring Boot", "Backend"),
                    new Skill("REST APIs", "Backend"), new Skill("MySQL", "Data"),
                    new Skill("React", "Frontend"), new Skill("TypeScript", "Frontend"),
                    new Skill("UI/UX Design", "Design"), new Skill("Python", "AI"),
                    new Skill("Machine Learning", "AI"), new Skill("RAG", "AI"),
                    new Skill("Cloud Deployment", "DevOps"), new Skill("Docker", "DevOps"),
                    new Skill("Git", "Tools"), new Skill("Testing", "Backend"),
                    new Skill("Communication", "Professional"), new Skill("Project Planning", "Professional"),
                    new Skill("Data Visualization", "Data"), new Skill("Cybersecurity", "Security")));

            projects.saveAll(List.of(
                    new Project("Smart Agriculture Monitoring", "Build a sensor-backed crop monitoring dashboard.", "RECRUITING"),
                    new Project("E-Commerce Platform", "Create a secure marketplace with product discovery.", "IN_PROGRESS"),
                    new Project("AI Chatbot for Education", "Ground answers in verified learning resources.", "RECRUITING"),
                    new Project("Campus Event Hub", "Centralize student events and registrations.", "RECRUITING"),
                    new Project("Public Health Dashboard", "Visualize community health indicators.", "IN_PROGRESS"),
                    new Project("Green Transport Planner", "Help commuters compare sustainable routes.", "RECRUITING"),
                    new Project("Local Business Directory", "Connect local businesses with student developers.", "PLANNING"),
                    new Project("Career Portfolio Builder", "Generate accessible evidence-based portfolios.", "IN_PROGRESS"),
                    new Project("Peer Tutor Network", "Match students for structured peer learning.", "RECRUITING"),
                    new Project("Library Resource Finder", "Improve search across campus resources.", "PLANNING"),
                    new Project("Accessible Campus Map", "Map routes and facilities for every learner.", "RECRUITING"),
                    new Project("Volunteer Match", "Connect students to meaningful opportunities.", "IN_PROGRESS"),
                    new Project("Finance Learning Lab", "Teach personal finance through interactive lessons.", "PLANNING"),
                    new Project("Open Source Starter", "Guide contributors through their first PR.", "RECRUITING"),
                    new Project("Wellbeing Check-in", "Create a privacy-conscious student wellbeing tool.", "PLANNING")));

            modules.saveAll(List.of(
                    new LearningModule("Java Fundamentals", "Backend", 45),
                    new LearningModule("Spring Boot Essentials", "Backend", 60),
                    new LearningModule("Database Design", "Data", 50),
                    new LearningModule("REST API Development", "Backend", 55),
                    new LearningModule("React Foundations", "Frontend", 45),
                    new LearningModule("TypeScript for Teams", "Frontend", 40),
                    new LearningModule("UI Design Systems", "Design", 35),
                    new LearningModule("Python for AI", "AI", 60),
                    new LearningModule("Machine Learning Basics", "AI", 75),
                    new LearningModule("Building RAG Applications", "AI", 70),
                    new LearningModule("Docker Deployment", "DevOps", 40),
                    new LearningModule("Cloud Fundamentals", "DevOps", 55),
                    new LearningModule("Testing Spring Applications", "Backend", 45),
                    new LearningModule("Git Collaboration", "Tools", 30),
                    new LearningModule("Secure API Design", "Security", 50)));

            Instant now = Instant.now();
            activities.saveAll(List.of(
                    new Activity("PROJECT", "New project assigned", "Smart Agriculture Monitoring", "blue", now.minus(2, ChronoUnit.HOURS)),
                    new Activity("MODULE", "Module completed", "Spring Boot Essentials", "purple", now.minus(4, ChronoUnit.HOURS)),
                    new Activity("PLACEMENT", "Placement drive", "TCS · Java Developer", "orange", now.minus(1, ChronoUnit.DAYS)),
                    new Activity("MENTOR", "New message from mentor", "Your Spring Boot roadmap was updated", "green", now.minus(2, ChronoUnit.DAYS)),
                    new Activity("SKILL", "Skill added", "REST APIs added to your profile", "blue", now.minus(3, ChronoUnit.DAYS)),
                    new Activity("PROJECT", "Team invitation", "Campus Event Hub invited you to join", "purple", now.minus(4, ChronoUnit.DAYS)),
                    new Activity("GOAL", "Learning goal created", "Build a production-ready API", "orange", now.minus(5, ChronoUnit.DAYS)),
                    new Activity("MODULE", "Module started", "Database Design", "green", now.minus(6, ChronoUnit.DAYS)),
                    new Activity("PROJECT", "Project milestone", "E-Commerce Platform completed sprint one", "blue", now.minus(7, ChronoUnit.DAYS)),
                    new Activity("MENTOR", "Mentor feedback", "Review your authentication flow", "purple", now.minus(8, ChronoUnit.DAYS)),
                    new Activity("SKILL", "Skill updated", "Java proficiency updated to advanced", "green", now.minus(9, ChronoUnit.DAYS)),
                    new Activity("MODULE", "Module completed", "Git Collaboration", "orange", now.minus(10, ChronoUnit.DAYS)),
                    new Activity("PROJECT", "New project opened", "Accessible Campus Map is recruiting", "blue", now.minus(11, ChronoUnit.DAYS)),
                    new Activity("GOAL", "Goal milestone", "REST API goal reached 75%", "purple", now.minus(12, ChronoUnit.DAYS)),
                    new Activity("PLACEMENT", "Opportunity saved", "Backend Developer · Infosys", "green", now.minus(13, ChronoUnit.DAYS)),
                    new Activity("MODULE", "Module started", "Building RAG Applications", "orange", now.minus(14, ChronoUnit.DAYS)),
                    new Activity("PROJECT", "Project update", "Peer Tutor Network scope approved", "blue", now.minus(15, ChronoUnit.DAYS)),
                    new Activity("MENTOR", "Conversation started", "Discussed Spring Security", "purple", now.minus(16, ChronoUnit.DAYS)),
                    new Activity("SKILL", "Skill mapped", "Docker mapped to DevOps", "green", now.minus(17, ChronoUnit.DAYS)),
                    new Activity("MODULE", "Module completed", "Java Fundamentals", "orange", now.minus(18, ChronoUnit.DAYS))));
        };
    }
}
