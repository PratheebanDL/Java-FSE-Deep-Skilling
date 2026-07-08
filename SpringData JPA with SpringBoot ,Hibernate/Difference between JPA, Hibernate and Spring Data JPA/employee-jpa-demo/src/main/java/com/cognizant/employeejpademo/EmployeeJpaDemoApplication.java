package com.cognizant.employeejpademo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.employeejpademo.hibernate.HibernateEmployeeDAO;
import com.cognizant.employeejpademo.model.Employee;
import com.cognizant.employeejpademo.service.EmployeeService;

@SpringBootApplication
public class EmployeeJpaDemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeJpaDemoApplication.class);

	private static EmployeeService employeeService;

	public static void main(String[] args) {
		LOGGER.info("Inside main");

		ApplicationContext context = SpringApplication.run(EmployeeJpaDemoApplication.class, args);
		employeeService = context.getBean(EmployeeService.class);

		testPlainHibernate();
		testSpringDataJpa();
	}

	/** "Before" picture: raw Hibernate Session/Transaction, matching the notes. */
	private static void testPlainHibernate() {
		LOGGER.info("--- Plain Hibernate ---");
		HibernateEmployeeDAO dao = new HibernateEmployeeDAO();
		Integer id = dao.addEmployee(new Employee("Priya Nair", "Finance", 62000.00));
		LOGGER.debug("Saved employee id (plain Hibernate)={}", id);
		dao.close();
	}

	/** "After" picture: Spring Data JPA repository + service, matching the notes. */
	private static void testSpringDataJpa() {
		LOGGER.info("--- Spring Data JPA ---");
		Employee saved = employeeService.addEmployee(new Employee("Karan Shah", "Engineering", 81000.00));
		LOGGER.debug("Saved employee (Spring Data JPA)={}", saved);

		List<Employee> employees = employeeService.getAllEmployees();
		LOGGER.debug("All employees={}", employees);
	}
}
