package com.cognizant.employeejpademo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.employeejpademo.model.Employee;
import com.cognizant.employeejpademo.repository.EmployeeRepository;

/**
 * Spring Data JPA style: no Session, no Transaction object, no try/catch/finally.
 * Spring generates the JpaRepository implementation at runtime and
 * @Transactional lets Spring's transaction manager handle commit/rollback.
 */
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Transactional
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Transactional
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
}
