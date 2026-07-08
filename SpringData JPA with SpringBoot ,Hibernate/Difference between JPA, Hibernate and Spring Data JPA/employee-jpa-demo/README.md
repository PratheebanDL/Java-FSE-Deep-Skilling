# employee-jpa-demo — JPA vs Hibernate vs Spring Data JPA

A single Spring Boot project that demonstrates all three layers side by side
using one `Employee` entity:

| Layer | Role | Where it shows up here |
|---|---|---|
| **JPA** | Specification only (annotations, contracts) | `@Entity`, `@Id`, `@Column`, `@Table` on `Employee.java` |
| **Hibernate** | Concrete ORM implementation of JPA | `HibernateEmployeeDAO.java` — raw `SessionFactory`/`Session`/`Transaction` |
| **Spring Data JPA** | Abstraction over a JPA provider (Hibernate here); removes boilerplate | `EmployeeRepository.java` + `EmployeeService.java` |

Both the plain-Hibernate path and the Spring-Data-JPA path use the **same**
`Employee` entity and the **same** database table — only the code that talks
to the database differs.

## Project structure

```
employee-jpa-demo/
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/com/cognizant/employeejpademo
    │   │   ├── EmployeeJpaDemoApplication.java   # main(); runs both comparisons
    │   │   ├── model/Employee.java               # @Entity shared by both paths
    │   │   ├── hibernate/HibernateEmployeeDAO.java  # plain Hibernate (Session/Transaction)
    │   │   ├── repository/EmployeeRepository.java   # Spring Data JPA (JpaRepository)
    │   │   └── service/EmployeeService.java         # @Service + @Transactional
    │   └── resources
    │       ├── application.properties   # datasource + Hibernate config for Spring Data JPA
    │       ├── hibernate.cfg.xml        # separate config used ONLY by HibernateEmployeeDAO
    │       └── schema.sql               # DDL + seed data
    └── test
        └── java/com/cognizant/employeejpademo/EmployeeJpaDemoApplicationTests.java
```

## 1. Create the database

```
mysql -u root -p < src/main/resources/schema.sql
```

Or manually:

```sql
create schema jpademo;
use jpademo;

create table employee(
    emp_id     int primary key auto_increment,
    emp_name   varchar(100) not null,
    emp_dept   varchar(50),
    emp_salary decimal(10,2)
);
```

## 2. Configure connections

Both `application.properties` (Spring Data JPA) and `hibernate.cfg.xml` (plain
Hibernate DAO) point at `jdbc:mysql://localhost:3306/jpademo` with `root`/`root`
by default — update both if your credentials differ.

## 3. Import into Eclipse / build / run

```
mvn clean package
mvn spring-boot:run
```

or run `EmployeeJpaDemoApplication.java` directly.

## What happens on startup

`main()` calls two methods back to back so you can compare the logs:

1. **`testPlainHibernate()`** — builds a `SessionFactory` from `hibernate.cfg.xml`,
   opens a `Session`, begins a `Transaction`, saves an `Employee`, commits, and
   closes the session — all written out explicitly, with manual rollback on
   `HibernateException`.
2. **`testSpringDataJpa()`** — calls `employeeService.addEmployee(...)`, which
   just delegates to `employeeRepository.save(...)`. Spring generates the
   `EmployeeRepository` implementation at runtime and `@Transactional` hands
   transaction management to Spring's `JpaTransactionManager` — no `Session`,
   `Transaction`, or `try/catch/finally` in your code.

Enable `logging.level.org.hibernate.SQL=trace` (already set) to see that both
paths ultimately generate the same SQL — the difference is entirely in how
much plumbing code *you* have to write.
