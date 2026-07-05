====================================================================
NOTE: This is a Maven pom.xml file (XML, not Java). It is saved
with a .java extension for convenience per your request, but should
be renamed to "pom.xml" and placed at the project root when used.
====================================================================


// ====================================================================
// FILE: pom.xml
// Path: pom.xml  (project root)
// ====================================================================

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                              http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.library</groupId>
    <artifactId>LibraryManagement</artifactId>
    <version>1.0.0</version>
    <packaging>war</packaging>

    <name>LibraryManagement</name>
    <description>Library Management System using Spring Context, AOP, and WebMVC</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring.version>5.3.30</spring.version>
    </properties>

    <dependencies>

        <!-- Spring Context: core IoC container, ApplicationContext, bean wiring -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring AOP: aspect-oriented programming support -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring WebMVC: MVC framework for building web applications -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Servlet API (provided by the servlet container at runtime) -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>

        <!-- Logging -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.36</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.36</version>
        </dependency>

    </dependencies>

    <build>
        <finalName>LibraryManagement</finalName>
        <plugins>

            <!-- Maven Compiler Plugin configured for Java 1.8 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>

        </plugins>
    </build>
</project>
