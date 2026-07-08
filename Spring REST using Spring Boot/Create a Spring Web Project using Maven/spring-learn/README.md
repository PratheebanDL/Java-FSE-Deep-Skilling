# spring-learn — Spring Web Quick Example

A minimal Spring Boot project created via [start.spring.io](https://start.spring.io/)
with **Spring Boot DevTools** and **Spring Web**, including a working REST
endpoint and tests, so the whole request/response cycle can be exercised.

## Prerequisites
- Eclipse IDE for Enterprise Java Developers
- Maven 3.6+
- Java 8+

## 1. Import into Eclipse

`File > Import > Maven > Existing Maven Projects` and select this `spring-learn` folder.

## 2. Build

```
mvn clean package
```

If you're behind a corporate proxy, add proxy flags, e.g.:

```
mvn clean package -Dhttp.proxyHost=proxy.cognizant.com -Dhttp.proxyPort=6050 -Dhttps.proxyHost=proxy.cognizant.com -Dhttps.proxyPort=6050 -Dhttp.proxyUser=123456
```

## 3. Run

```
mvn spring-boot:run
```

or run `SpringLearnApplication.java` directly from your IDE. Look for the
`"Inside main"` log line to confirm `main()` was invoked, followed by
`"SpringLearnApplication started successfully"`.

## 4. Try the endpoint

Once running (embedded Tomcat on port 8080 by default):

```
http://localhost:8080/hello
http://localhost:8080/hello?name=Cognizant
```

## Project structure

```
spring-learn/
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/com/cognizant/springlearn
    │   │   ├── SpringLearnApplication.java   # main() + @SpringBootApplication
    │   │   └── controller/HelloController.java  # @RestController, @GetMapping
    │   └── resources
    │       ├── application.properties        # logging + server port config
    │       ├── static/                        # static assets (css/js/images)
    │       └── templates/                     # server-side view templates
    └── test
        └── java/com/cognizant/springlearn
            ├── SpringLearnApplicationTests.java   # context load test
            └── controller/HelloControllerTests.java  # MockMvc endpoint test
```

## SME walkthrough notes

**1. `src/main/java`** — application code: the main class and the
`controller` package containing `@RestController` classes.

**2. `src/main/resources`** — application configuration: `application.properties`,
plus conventional `static/` (served as-is, e.g. CSS/JS/images) and `templates/`
(server-rendered views, e.g. Thymeleaf) folders, even though this demo only
uses REST responses.

**3. `src/test/java`** — test code: `SpringLearnApplicationTests` verifies the
Spring context loads; `HelloControllerTests` uses `MockMvc` to hit `/hello`
without starting a real server.

**4. `SpringLearnApplication.java` — `main()` walkthrough**
`SpringApplication.run(SpringLearnApplication.class, args)` bootstraps the
Spring `ApplicationContext`, triggers auto-configuration (embedded Tomcat +
Spring MVC, because `spring-boot-starter-web` is on the classpath), performs
component scanning from this class's package downward, and starts the
embedded server — all in one call.

**5. Purpose of `@SpringBootApplication`**
A meta-annotation combining:
- `@Configuration` — this class can define beans.
- `@EnableAutoConfiguration` — Spring Boot auto-configures beans based on
  what's on the classpath (e.g. seeing `spring-boot-starter-web` triggers
  DispatcherServlet, embedded Tomcat, Jackson message converters, etc.).
- `@ComponentScan` — scans this package and sub-packages for `@Component`,
  `@Service`, `@Repository`, `@RestController`, etc.

**6. `pom.xml`**
- `<parent>` — inherits from `spring-boot-starter-parent`, which manages
  dependency versions so you don't have to pin them individually.
- `spring-boot-starter-web` — pulls in Spring MVC, embedded Tomcat, and Jackson
  for JSON.
- `spring-boot-devtools` — enables automatic restart on code changes and
  disables template caching during development; marked `optional` so it
  doesn't get bundled into production artifacts that depend on this one.
- `spring-boot-starter-test` — JUnit 5, Mockito, MockMvc, AssertJ, etc.
- `spring-boot-maven-plugin` — repackages the build into an executable
  "fat jar" containing an embedded server.

Open Eclipse's **Dependency Hierarchy** view on `pom.xml` to see how each
starter transitively pulls in its own tree of libraries (e.g.
`spring-boot-starter-web` → `spring-webmvc`, `tomcat-embed-core`,
`jackson-databind`, ...).
