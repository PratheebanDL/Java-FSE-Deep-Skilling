# orm-learn — Spring Data JPA Quick Example

A minimal Spring Boot + Spring Data JPA + Hibernate + MySQL project demonstrating
entity mapping, repository, and service layers using a `Country` table.

## Prerequisites
- MySQL Server 8.0
- MySQL Workbench 8 (optional, for GUI access)
- Java 8+
- Maven 3.6+
- Eclipse IDE for Enterprise Java Developers (or any IDE with Maven support)

## 1. Create the database schema and table

Log in to MySQL and run the script in `src/main/resources/schema.sql`:

```
mysql -u root -p < src/main/resources/schema.sql
```

Or manually:

```sql
create schema ormlearn;
use ormlearn;

create table country(
    co_code varchar(2) primary key,
    co_name varchar(50)
);

insert into country values ('IN', 'India');
insert into country values ('US', 'United States of America');
```

## 2. Configure the database connection

Edit `src/main/resources/application.properties` if your MySQL username/password
differ from the defaults (`root` / `root`).

## 3. Import into Eclipse

`File > Import > Maven > Existing Maven Projects` and select this `orm-learn` folder.

## 4. Build

```
mvn clean package
```

(If you're behind a corporate proxy, add proxy flags, e.g.:
`-Dhttp.proxyHost=<host> -Dhttp.proxyPort=<port> -Dhttps.proxyHost=<host> -Dhttps.proxyPort=<port>`)

## 5. Run

```
mvn spring-boot:run
```

or run `OrmLearnApplication.java` directly from your IDE.

On startup, the application:
1. Logs `"Inside main"` to confirm `main()` was invoked.
2. Fetches the Spring `ApplicationContext` and retrieves the `CountryService` bean.
3. Calls `getAllCountries()` and logs the retrieved list of countries (visible at
   `debug` log level).

## Project structure

```
orm-learn/
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/com/cognizant/ormlearn
    │   │   ├── OrmLearnApplication.java      # main() + test invocation
    │   │   ├── model/Country.java            # @Entity mapped to `country` table
    │   │   ├── repository/CountryRepository.java  # extends JpaRepository
    │   │   └── service/CountryService.java   # @Service, @Transactional business logic
    │   └── resources
    │       ├── application.properties        # datasource + hibernate config
    │       └── schema.sql                     # DDL + seed data
    └── test
        └── java/com/cognizant/ormlearn/OrmLearnApplicationTests.java
```

## Key annotations explained
- `@Entity` — marks the class as a JPA entity mapped to a database table.
- `@Table(name=...)` — specifies the mapped table name.
- `@Id` — marks the primary key field.
- `@Column(name=...)` — maps a field to a specific column.
- `@Repository` — marks the interface as a Spring Data repository bean.
- `@Service` — marks the class as a Spring-managed service bean.
- `@Transactional` — wraps the method in a database transaction.
- `@SpringBootApplication` — enables auto-configuration, component scanning, and
  marks this as the application's entry point.
