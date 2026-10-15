# SkillBridge backend

Spring Boot backend for SkillBridge, using Java 17+, Maven, MySQL, and Spring Data JPA.

## Run locally

The repository includes a development `backend/.env` used by Docker Compose.
Change the credentials and JWT secret before using this setup outside local
development.

To start MySQL and the backend together from the repository root:

```powershell
docker compose up --build
```

The API runs on `http://localhost:8080`.

When `SEED_DATA_ENABLED=true` and the database is empty, startup creates:

- 17 users: 16 students and 1 admin
- 18 skills
- 15 projects
- 15 learning modules
- 20 activity records

This provides the records needed by the dashboard while keeping seed data
repeatable: it runs only when the users table is empty.

To run the application directly with Maven, export the variables from
`backend/.env` in your shell and ensure MySQL is running:

```powershell
mvn spring-boot:run
```

## Endpoints

- `GET /api/status` - basic API status
- `GET /api/dashboard` - live dashboard counts and recent activities
- `GET /actuator/health` - application health
- `GET /actuator/info` - application information

The current project provides the application foundation and configuration. Domain modules such as users, authentication, skills, and mentoring can be added on top of this foundation.

## Tests

The context test uses the `test` profile and does not require a running MySQL instance:

```powershell
mvn test
```
