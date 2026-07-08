package com.cognizant.employeejpademo.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognizant.employeejpademo.model.Employee;

/**
 * Plain Hibernate style (no Spring, no Spring Data JPA).
 * This is the "before" picture: every session, transaction, and
 * exception-handling concern is written out by hand.
 *
 * Compare this class to EmployeeRepository + EmployeeService, which do the
 * same job (create an Employee) with almost no boilerplate.
 */
public class HibernateEmployeeDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateEmployeeDAO.class);

	private final SessionFactory factory;

	public HibernateEmployeeDAO() {
		this.factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	/* Method to CREATE an employee in the database */
	public Integer addEmployee(Employee employee) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeId = null;
		try {
			tx = session.beginTransaction();
			employeeId = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			LOGGER.error("Error saving employee", e);
		} finally {
			session.close();
		}
		return employeeId;
	}

	public void close() {
		factory.close();
	}
}
