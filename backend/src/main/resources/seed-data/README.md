# Seed data collections

These CSV collections document the development dataset loaded by
`SeedDataConfiguration`. They are intentionally kept in the application
resources so the seed is reviewable and can later be replaced by an import
pipeline without changing the page APIs.

Collections:

- `users.csv` - 16 students and 1 admin; all use password `1234` in development
- `skills.csv` - 18 capability records
- `projects.csv` - 15 project records
- `learning-modules.csv` - 15 learning records
- `activities.csv` - 20 activity records

Set `SEED_DATA_ENABLED=false` to disable startup seeding.
