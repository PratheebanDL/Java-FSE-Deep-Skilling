# Exercise 3 – Eureka Discovery Server + Spring Cloud API Gateway

This folder contains 5 independent Spring Boot Maven projects. Import each
one into Eclipse separately (File > Import > Existing Maven Projects).

| # | Project               | Port | Purpose                                             |
|---|-----------------------|------|------------------------------------------------------|
| 1 | eureka-discovery-server | 8761 | Service registry (Eureka Server)                  |
| 2 | account-service       | 8080 | Bank account REST API, registers with Eureka        |
| 3 | loan-service          | 8081 | Bank loan REST API, registers with Eureka           |
| 4 | greet-service         | 8082 | "Hello World" REST API, registers with Eureka       |
| 5 | api-gateway           | 9090 | Spring Cloud Gateway routing to registered services |

## Build order (command line, inside each folder)
```
mvn clean package
```

## Run order (IMPORTANT – Eureka server must start first)

1. Start **eureka-discovery-server** first. Wait until it's fully up, then
   open http://localhost:8761 — the "Instances currently registered with
   Eureka" list should be empty.

2. Start **account-service**. Refresh http://localhost:8761 — you should
   see `ACCOUNT-SERVICE` listed.
   Test directly: http://localhost:8080/accounts/00987987973432

3. Start **loan-service**. Refresh http://localhost:8761 — you should
   see `LOAN-SERVICE` listed.
   Test directly: http://localhost:8081/loans/H00987987972342

4. Start **greet-service**. Refresh http://localhost:8761 — you should
   see `GREET-SERVICE` listed.
   Test directly: http://localhost:8082/greet → returns "Hello World"

5. Start **api-gateway**. Refresh http://localhost:8761 — you should
   see `API-GATEWAY` listed.

## Testing through the API Gateway

Once all services are registered, you can reach any of them through the
gateway on port 9090, using the lower-cased service name as the path
prefix (this is enabled via
`spring.cloud.gateway.discovery.locator.enabled=true`):

```
http://localhost:9090/greet-service/greet
http://localhost:9090/account-service/accounts/00987987973432
http://localhost:9090/loan-service/loans/H00987987972342
```

## Global Logging Filter

`api-gateway` includes `LogFilter` (in package
`com.cognizant.apigateway.filter`), a `GlobalFilter` that logs the HTTP
method and path of every request that passes through the gateway. Check
the **api-gateway console** in Eclipse after hitting any of the URLs
above — you'll see a log line like:

```
INFO ... Incoming request -> Method: GET, Path: /greet-service/greet
```

## Switching between running consoles in Eclipse

With 5 applications running at once, use the monitor icon (🖥) in the
Eclipse Console view to switch between each application's console output.
