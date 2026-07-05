-- Table structure must exist before Hibernate's ddl-auto=validate check runs.
DROP TABLE IF EXISTS country;

CREATE TABLE country (
    co_code VARCHAR(2)   NOT NULL PRIMARY KEY,
    co_name VARCHAR(255) NOT NULL
);
