package com.skillbridge.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.context.annotation.Profile;

import com.skillbridge.domain.Role;
import com.skillbridge.domain.User;
import com.skillbridge.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@Profile("!test")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse signup(@Valid @RequestBody SignupRequest request) {
        if (users.findByEmailIgnoreCase(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account already exists for this email");
        }
        User user = users.save(new User(request.name().trim(), request.email().trim().toLowerCase(),
                passwordEncoder.encode(request.password()), Role.STUDENT));
        return AuthResponse.from(user);
    }

    @PostMapping("/signin")
    public AuthResponse signin(@Valid @RequestBody SigninRequest request) {
        User user = users.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        boolean seededDevelopmentPassword = "{noop}demo".equals(user.getPasswordHash())
                && "demo".equals(request.password());
        if (!seededDevelopmentPassword && !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return AuthResponse.from(user);
    }

    public record SignupRequest(@NotBlank String name, @Email @NotBlank String email,
            @NotBlank @Size(min = 4) String password) {
    }

    public record SigninRequest(@Email @NotBlank String email, @NotBlank @Size(min = 4) String password) {
    }

    public record AuthResponse(Long id, String name, String email, Role role) {
        static AuthResponse from(User user) {
            return new AuthResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
        }
    }
}
