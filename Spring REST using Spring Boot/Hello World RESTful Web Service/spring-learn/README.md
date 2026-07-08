# spring-learn

A Spring Boot application containing two exercises:

1. **Loading a `SimpleDateFormat` bean from Spring XML configuration**
   (`date-format.xml` + `displayDate()`).
2. **A "Hello World" RESTful web service** built with Spring Web
   (`HelloController`), served on an embedded Tomcat at port **8083**.

## Project layout

```
spring-learn/
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com/example/springlearn/
    │   │       ├── SpringLearnApplication.java      (Spring Boot entry point + XML bean demo)
    │   │       └── controller/
    │   │           └── HelloController.java         (GET /hello)
    │   └── resources
    │       ├── application.properties               (server.port=8083)
    │       └── date-format.xml                       (SimpleDateFormat bean)
    └── test
        └── java
            └── com/example/springlearn/
                ├── SpringLearnApplicationTest.java
                └── controller/
                    └── HelloControllerTest.java
```

## Package name note

The exercise text referenced the package
`com.cognizant.spring-learn.controller.HelloController`. A hyphen (`-`) is
**not a legal character** in a Java package name, so this project uses
`com.example.springlearn.controller.HelloController` instead, keeping it
consistent with the rest of the codebase. If your organization's real
convention is `com.cognizant.springlearn.controller` (no hyphen), just
rename the package/folders — everything else stays the same.

## The REST service

**`HelloController.java`**

```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        logger.info("START - sayHello()");
        String response = "Hello World!!";
        logger.info("END - sayHello()");
        return response;
    }
}
```

- **Method:** GET
- **URL:** `/hello`
- **Response body:** `Hello World!!`
- Start/end log statements are written via SLF4J at `INFO` level, so you'll
  see them in the console when the endpoint is hit.

## How to run

### Option 1: Maven

```bash
cd spring-learn
mvn spring-boot:run
```

### Option 2: Build and run the jar

```bash
mvn clean package
java -jar target/spring-learn.jar
```

### Option 3: IDE

Import as a Maven project, then run `SpringLearnApplication` as a **Java
Application** (it's annotated with `@SpringBootApplication`, so it starts
the embedded Tomcat server automatically).

On startup you should see console output similar to:

```
Parsed date: Mon Dec 31 00:00:00 <TZ> 2018
Formatted back using the same bean: 31/12/2018
...
Tomcat started on port(s): 8083 (http)
```

## Testing the endpoint

### In a browser (Chrome)

1. Navigate to `http://localhost:8083/hello`.
2. The page displays: `Hello World!!`
3. Open **DevTools → Network tab** (F12, then Network), reload the page,
   and click the `hello` request. The **Headers** panel shows:
   - **Request Headers** — e.g. `Host`, `User-Agent`, `Accept`,
     `Accept-Encoding`, `Connection`, sent by the browser.
   - **Response Headers** — e.g. `Content-Type: text/plain;charset=UTF-8`,
     `Content-Length`, `Date`, `Connection: keep-alive`, sent back by the
     embedded Tomcat/Spring MVC server. `Content-Type` in particular
     confirms Spring returned the string as plain text.

### In Postman

1. Create a new **GET** request to `http://localhost:8083/hello`.
2. Click **Send**. The response body panel shows `Hello World!!`.
3. Click the **Headers** tab (next to the response body) to see the same
   response headers the browser reported: `Content-Type`, `Content-Length`,
   `Date`, `Keep-Alive`, `Connection`, etc. Postman lists these clearly in a
   key/value table, which is often easier to read than the raw view in
   browser DevTools.

Both tools are hitting the exact same endpoint, so the header values you
see should match (aside from minor differences in what each client sends
as request headers).

## Console log output

Each time `/hello` is called, you should see in the console:

```
INFO ... HelloController : START - sayHello()
INFO ... HelloController : END - sayHello()
```

## Running the tests

```bash
mvn test
```

This runs:
- `SpringLearnApplicationTest` — verifies the `dateFormat` bean loads from
  `date-format.xml` and parses `31/12/2018` correctly.
- `HelloControllerTest` — uses `MockMvc` to verify `GET /hello` returns
  HTTP 200 with body `Hello World!!`.
