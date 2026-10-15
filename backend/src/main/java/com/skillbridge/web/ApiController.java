package com.skillbridge.web;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/status")
    public ApiStatus status() {
        return new ApiStatus("SkillBridge API is running", Instant.now());
    }

    public record ApiStatus(String message, Instant timestamp) {
    }
}
