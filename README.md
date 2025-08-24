# DBO (Test Assignment: DBO Core for Client Balance Operations)

[Test assignment estimated at 6-8 hours](Task.pdf)

## Solution Notes

### What Was Accomplished

1) Implemented pessimistic locking for balance updates.
2) Added a custom USER_ID claim to the JWT.
3) Integrated Testcontainers and implemented component and unit tests.
4) Configured developer-friendly tools: Lombok, MapStruct, Flyway.
5) Set up Swagger (OpenAPI) with authentication support for making authorized requests using a JWT token.
6) Added Docker and Docker Compose files for one-command building and running the entire solution with its infrastructure.

### What I Didn't Have Time to Improve

1) C.UD Operations and Flexible Search: Only had time to implement full CRUD for the Phone entity and Read operations for the Client. Create, Update, Delete operations and advanced search for clients are missing.
2) Redis Cache: I considered adding a Redis cache to demonstrate the ability, as it was mentioned in the assignment. However, based on the service's potential needs, if the focus is flexible, operational client search, a solution based on Elasticsearch would be more suitable.
3) Comprehensive Validation: Would need to dedicate more time to input and business logic validation.
4) Enhanced Logging: Added basic logging for debugging during development; it needs a more structured approach for production.
5) JavaDocs & Detailed API Docs: The Swagger API descriptions and JavaDoc comments could be more comprehensive.

### Additional Important Items Not Explicitly Required

1) Password Storage: Passwords are stored encrypted (hashed) for security.
2) Table Naming: Used the table name CLIENT instead of USER to avoid conflicts with the reserved keyword in PostgreSQL.

## Build

Prerequisites

1) Java 11
2) Maven 3.8
3) Docker

Run the following command in the project root:
```
mvn clean package
```

## Run

### Via Docker Compose

Prerequisites: docker, docker-buildx, and docker-compose must be installed.

To start the application and all its infrastructure (database, etc.), run:

`docker-compose up -d`

## Using the Service

Authentication is done via email. [A list of pre-loaded users is available in the source code](src/main/resources/db/migration/V2__init_data.sql).

### Swagger (OpenAPI UI)

http://localhost:8080/swagger-ui/index.html

### Postman

[A Postman collection is provided for testing the API.](task_dbo.postman_collection.json)

