# Week - 4: Spring Boot Microservices Architecture

This week covers building scalable microservices using Spring Boot with best practices.

## Project Structure

### Account Microservice
- REST API for account management
- Features: Create, Read, Update, Delete accounts
- Port: 8001

### Loan Microservice  
- REST API for loan management
- Features: Apply loans, track status
- Port: 8002

## Technologies
- Spring Boot 3.2.5
- Spring Data JPA
- MySQL Database
- Maven
- Java 17

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Build
```bash
mvn clean install
```

### Run Account Service
```bash
cd account-service
mvn spring-boot:run
```

### Run Loan Service
```bash
cd loan-service
mvn spring-boot:run
```

## API Documentation
- Account Service: http://localhost:8001/swagger-ui.html
- Loan Service: http://localhost:8002/swagger-ui.html

## Modules

1. **account-service** - Account management microservice
2. **loan-service** - Loan management microservice
3. **common** - Shared utilities and models

