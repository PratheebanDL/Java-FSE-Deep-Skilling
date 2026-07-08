package com.cognizant.employeejpademo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This single @Entity class is used by BOTH comparison paths in this project:
 *  - HibernateEmployeeDAO (plain Hibernate, via hibernate.cfg.xml + SessionFactory)
 *  - EmployeeRepository   (Spring Data JPA, via JpaRepository)
 *
 * That's the point of the demo: JPA annotations describe the mapping once,
 * and either a hand-written Hibernate DAO or a generated Spring Data
 * repository can use it.
 */
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private Integer id;

	@Column(name = "emp_name")
	private String name;

	@Column(name = "emp_dept")
	private String department;

	@Column(name = "emp_salary")
	private Double salary;

	public Employee() {
	}

	public Employee(String name, String department, Double salary) {
		this.name = name;
		this.department = department;
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", salary=" + salary + "]";
	}
}
