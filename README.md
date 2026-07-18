# SPORTS DATA

Sports Data is a study and reference project focused on modeling and exposing sports-related information for competitions such as NFL, Brasileirão, Formula 1, FIA WEC, and others.

## Current stack

- Java 25 (toolchain configured in Gradle)
- Gradle 9.0.0 via the project wrapper
- Spring Boot 3.5.6
- Spring Web, Spring Data JPA, Validation API
- PostgreSQL 15 via Docker Compose
- JUnit 5 for testing

## Prerequisites

- JDK 25 or later
- Docker and Docker Compose (recommended for the database)

## Quick start

1. Start the PostgreSQL container:

```bash
docker compose up -d pg_sports
```

2. Set the database environment variables:

```bash
export DATABASE_URL='jdbc:postgresql://localhost:5432/sport_data_dev_db'
export DATABASE_USERNAME='dev-user'
export DATABASE_PASSWORD='password'
```

3. Run the application:

```bash
./gradlew bootRun
```

The application will be available at http://localhost:8080.

## Tests

```bash
./gradlew test
```

## Notes

The project uses the Gradle wrapper, so you do not need to install Gradle manually. The wrapper is already configured in [gradlew](gradlew) and [gradlew.bat](gradlew.bat).
