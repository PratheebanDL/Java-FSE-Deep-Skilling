# spring-learn

Demonstrates defining a shared `SimpleDateFormat` bean (`dd/MM/yyyy`) in a
Spring XML configuration file, instead of instantiating
`new SimpleDateFormat("dd/MM/yyyy")` in multiple places throughout an
application.

## Project layout

```
spring-learn/
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com/example/springlearn/SpringLearnApplication.java
    │   └── resources
    │       └── date-format.xml
    └── test
        └── java
            └── com/example/springlearn/SpringLearnApplicationTest.java
```

## Files

- **`date-format.xml`** — Spring bean configuration. Defines a bean with id
  `dateFormat` of class `java.text.SimpleDateFormat`, constructed with the
  pattern `dd/MM/yyyy`.
- **`SpringLearnApplication.java`** — Contains `displayDate()`, which:
  1. Creates a `ClassPathXmlApplicationContext` from `date-format.xml`.
  2. Retrieves the `dateFormat` bean via `context.getBean(...)`.
  3. Parses the string `31/12/2018` into a `Date` and prints it.
- **`SpringLearnApplicationTest.java`** — A JUnit test that loads the same
  bean and asserts the parsed date's year/month/day are correct.

## How to run

### Option 1: Maven (command line)

```bash
cd spring-learn
mvn compile exec:java
```

### Option 2: Build a runnable JAR

```bash
mvn package
java -jar target/spring-learn.jar
```

### Option 3: As an IDE "Java Application"

1. Import the project into your IDE (IntelliJ IDEA / Eclipse / STS) as a
   Maven project.
2. Let it download dependencies (`spring-context`, etc.).
3. Right-click `SpringLearnApplication.java` → **Run As → Java Application**.

## Expected console output

```
Parsed date: Mon Dec 31 00:00:00 <TZ> 2018
Formatted back using the same bean: 31/12/2018
```

(The exact `Date.toString()` representation depends on your JVM's default
time zone and locale, but the day/month/year will always correspond to
31 December 2018.)

## Run the test

```bash
mvn test
```
