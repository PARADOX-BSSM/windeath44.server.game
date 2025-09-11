# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.x game application using Java 17 with Gradle build system. The project implements game play history tracking functionality with MySQL database integration.

## Build and Development Commands

### Core Commands
- `./gradlew build` - Build the entire project
- `./gradlew test` - Run all tests
- `./gradlew bootRun` - Start the Spring Boot application
- `./gradlew clean` - Clean build artifacts

### Development Commands
- `./gradlew bootTestRun` - Run application with test runtime classpath
- `./gradlew bootJar` - Create executable JAR
- `./gradlew check` - Run all verification tasks

## Architecture

### Package Structure
```
windeath44.game/
├── GameApplication.java          # Main Spring Boot application class
├── domain/
│   └── gamePlayHistory/         # Game play history domain
│       └── model/
│           └── GamePlayHistory.java  # JPA entity for game results
└── global/                      # Cross-cutting concerns
    ├── aspect/
    │   └── LoggingAspect.java   # AOP logging (currently incomplete)
    ├── config/
    │   └── WebConfig.java       # CORS configuration for frontend
    ├── dto/
    │   ├── CursorPage.java      # Pagination utility
    │   └── ResponseDto.java     # Standard API response wrapper
    ├── error/
    │   ├── ErrorResponse.java
    │   ├── GlobalExceptionHandler.java  # Global exception handling
    │   └── exception/
    └── util/
        └── HttpUtil.java        # HTTP utilities
```

### Key Technologies
- **Spring Boot 3.5.5** with Spring Data JPA and Validation
- **Java 17** with Lombok for boilerplate reduction
- **MySQL** database with JPA auditing enabled
- **Gradle 8.x** build system

### Database Configuration
- JPA auditing is enabled via `@EnableJpaAuditing` in main application class
- Uses MySQL connector for database connectivity
- GamePlayHistory entity tracks: completion rate, combo, scoring metrics, and timestamps

### CORS Configuration
Frontend is configured to accept requests from multiple local development servers:
- localhost:5173 and various IP addresses on port 5173 (likely Vite dev servers)

## Development Notes

### Current State
- Basic Spring Boot structure is established
- GamePlayHistory domain model is implemented but appears to be work in progress
- Global exception handler exists but is incomplete (missing return statement)
- Logging aspect is defined but not fully implemented

### Testing
- Uses JUnit Platform with Spring Boot Test starter
- Run tests with `./gradlew test`