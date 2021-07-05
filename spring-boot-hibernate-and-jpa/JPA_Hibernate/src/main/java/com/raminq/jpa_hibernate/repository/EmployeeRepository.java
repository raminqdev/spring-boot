package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@Transactional
public class EmployeeRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final EntityManager em;

    @Autowired
    public EmployeeRepository(EntityManager em) {
        this.em = em;
    }

    public Employee findById(Long id) {
        return em.find(Employee.class, id);
    }

    public void insert(Employee employee) {
        em.persist(employee);
    }

    public List<Employee> retrieveAllEmployees() {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    public List<Employee> retrieveAllPartTimeEmployees() {
        return em.createQuery("select e from PartTimeEmployee e", Employee.class).getResultList();
    }

    public List<Employee> retrieveAllFullTimeEmployees() {
        return em.createQuery("select e from FullTimeEmployee e", Employee.class).getResultList();
    }


}
