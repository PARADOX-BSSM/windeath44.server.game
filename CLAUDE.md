# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a modern Spring Boot 3.5.5 game application using Java 17 with Gradle build system. The project implements a comprehensive rhythm game system with game play history tracking, ranking system, and async event processing. It follows Domain-Driven Design principles with clean architecture patterns.

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

## Tech Stack

### Core Technologies
- **Spring Boot 3.5.5** - Latest Spring Boot with Jakarta EE support
- **Java 17** - Modern LTS Java version with language level 17
- **Gradle 8.x** - Build system with wrapper
- **MySQL** - Production database with H2 for testing
- **Lombok** - Boilerplate code reduction

### Spring Modules & Dependencies
- **Spring Data JPA** - ORM and database abstraction layer
- **Spring Boot Starter Web** - REST API with embedded Tomcat
- **Spring Boot Starter Validation** - Jakarta validation support
- **Spring Boot Starter Test** - JUnit 5 testing framework with AssertJ
- **MySQL Connector/J** - MySQL database driver

### Key Features
- **JPA Auditing** enabled for automatic timestamp management
- **Async Processing** enabled for non-blocking event handling
- **CORS Configuration** for frontend integration
- **Event-Driven Architecture** with Spring Application Events

## Project Structure & Architecture

### Domain-Driven Design Package Structure
```
windeath44.game/
├── GameApplication.java                    # Main Spring Boot application (@EnableAsync, @EnableJpaAuditing)
├── domain/                                # Domain-driven design approach
│   ├── gamePlayHistory/                   # Game play tracking domain
│   │   ├── controller/                    # REST API endpoints
│   │   │   └── GamePlayHistoryController.java
│   │   ├── service/                       # Business logic layer
│   │   │   └── GamePlayHistoryService.java
│   │   ├── repository/                    # Data access layer
│   │   │   ├── GamePlayHistoryRepository.java
│   │   │   └── RhythmRankingRepository.java
│   │   ├── model/                         # JPA entities
│   │   │   ├── GamePlayHistory.java       # Game session tracking
│   │   │   └── type/
│   │   │       └── GamePlayHistoryState.java  # Enum: FAILED, CLEAR, FULL_COMBO, ALL_PERFECT
│   │   ├── dto/                           # Data transfer objects
│   │   │   ├── request/
│   │   │   │   └── GamePlayHistoryRequest.java
│   │   │   └── response/
│   │   │       └── GamePlayHistoryResponse.java
│   │   ├── mapper/                        # Entity-DTO mapping
│   │   │   └── GamePlayHistoryMapper.java
│   │   ├── event/                         # Domain events
│   │   │   └── GamePlayHistorySavedEvent.java
│   │   ├── exception/                     # Domain-specific exceptions
│   │   │   └── NotFoundGamePlayHistoryException.java
│   │   └── util/                          # Domain utilities
│   │       └── RankCalculator.java        # Completion rate to rank conversion
│   └── ranking/                           # Ranking system domain
│       ├── controller/
│       │   └── RankingController.java
│       ├── service/
│       │   └── RankingService.java        # Async event listener for ranking updates
│       ├── model/
│       │   └── RhythmRanking.java         # Best score per user per music
│       └── dto/
│           ├── RankingRequest.java
│           └── RankingResponse.java
└── global/                                # Cross-cutting concerns
    ├── config/
    │   └── WebConfig.java                 # CORS configuration
    ├── dto/
    │   ├── CursorPage.java                # Cursor-based pagination utility
    │   └── ResponseDto.java               # Standard API response wrapper (record)
    ├── error/
    │   ├── ErrorResponse.java
    │   ├── GlobalExceptionHandler.java    # Global exception handling
    │   └── exception/
    ├── util/
    │   └── HttpUtil.java                  # HTTP utilities
    └── aspect/
        └── LoggingAspect.java             # AOP logging aspect
```

### Architectural Patterns
- **Domain-Driven Design (DDD)** - Clear domain separation with bounded contexts
- **Layered Architecture** - Controller → Service → Repository → Entity
- **Event-Driven Architecture** - Async domain events for ranking system updates
- **CQRS-like patterns** - Separate read/write operations with optimized queries
- **Repository Pattern** - Spring Data JPA with custom native queries

## Database Design

### Core Entities

**GamePlayHistory Entity:**
- Tracks individual rhythm game play sessions
- Fields: `userId` (String), `musicId` (Long), `completionRate` (float), `combo`, scoring metrics (`perfectPlus`, `perfect`, `great`, `good`, `miss`)
- Auto-calculated `rank` field based on completion rate using `RankCalculator`
- `state` enum: `FAILED`, `CLEAR`, `FULL_COMBO`, `ALL_PERFECT`
- JPA auditing for `playedAt` timestamp

**RhythmRanking Entity:**
- Stores best completion rate per user per music
- Unique constraint: `(userId, musicId)` - one ranking per user per song
- JPA auditing for `createdAt` and `updatedAt`
- Updated asynchronously via domain events from game play history saves

**Key Database Features:**
- MySQL production database with H2 for testing
- JPA auditing enabled for automatic timestamp management
- Identity generation strategy for primary keys
- Unique constraints to prevent duplicate rankings

## API Design

### GamePlay History API (`/game/game-play-history`)
- `POST /` - Save game play history (requires `user-id` header)
- `DELETE /{id}` - Delete specific game history record
- `GET /my` - Get user's personal history with cursor pagination
- `GET /` - Get all game histories (admin view) with cursor pagination

### Ranking API (`/game/rankings`)
- `GET /` - Get rankings by music ID with cursor-based pagination
- Complex ranking calculation using CTEs for performance

### API Conventions
- Consistent `ResponseDto<T>` wrapper for all API responses
- User identification via `user-id` header (Long type converted to String internally)
- Cursor-based pagination with `CursorPage<T>` for scalable data access
- Jakarta validation on all request DTOs with Korean error messages
- RESTful URL patterns following Spring Boot conventions

## Coding Conventions

### Naming Conventions
- **Packages**: lowercase with domain-based grouping (`gamePlayHistory`, `ranking`)
- **Classes**: PascalCase with descriptive names (`GamePlayHistoryService`, `RankCalculator`)
- **Methods**: camelCase with action-oriented names (`calculateRank`, `handleGamePlayHistorySaved`)
- **Variables**: camelCase with meaningful names
- **DTOs**: Record-based with `Request`/`Response` suffixes

### Code Style & Patterns
- **Lombok Usage**: `@Builder`, `@Getter`, `@RequiredArgsConstructor` for clean code
- **Modern Java**: Records for DTOs, Optional for nullable returns, Stream API usage
- **Validation**: Comprehensive Jakarta validation with Korean error messages
- **Immutability**: Preference for immutable records and builder patterns
- **Dependency Injection**: Constructor injection with `@RequiredArgsConstructor`

### Business Logic Patterns
- **Rank Calculation**: Completion rate-based ranking (SSS+ ≥ 99.75%, down to D < 90%)
- **State Determination**: Auto-calculated based on scoring metrics (ALL_PERFECT, FULL_COMBO, CLEAR, FAILED)
- **Event-Driven Updates**: Domain events trigger async ranking updates
- **Transactional Management**: Service-level `@Transactional` annotations

### Advanced Features
- **Async Processing**: `@Async` event listeners for non-blocking operations
- **Complex Queries**: Native SQL with CTEs for performance-critical ranking queries
- **Cursor Pagination**: Custom implementation avoiding expensive offset-based pagination
- **Exception Handling**: Global exception handler with domain-specific exceptions

## Testing Strategy

### Testing Patterns
- **Integration Tests**: `@SpringBootTest` with `@ActiveProfiles("test")`
- **Test Database**: H2 in-memory with MySQL compatibility mode
- **BDD Style**: Clear Given-When-Then structure with Korean test descriptions
- **AssertJ**: Modern assertion library for readable and maintainable tests
- **Transactional Tests**: `@Transactional` for automatic rollback

### Test Coverage
- Service layer business logic testing
- Repository query testing
- Event processing testing
- API endpoint integration testing