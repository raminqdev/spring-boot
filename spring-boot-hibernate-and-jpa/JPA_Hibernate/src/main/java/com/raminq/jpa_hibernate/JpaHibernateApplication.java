package com.raminq.jpa_hibernate;

import com.raminq.jpa_hibernate.entity.*;
import com.raminq.jpa_hibernate.repository.CourseRepository;
import com.raminq.jpa_hibernate.repository.EmployeeRepository;
import com.raminq.jpa_hibernate.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JpaHibernateApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JpaHibernateApplication.class, args);
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private EmployeeRepository employeeRepository;
    private EntityManager em;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository,
                                    StudentRepository studentRepository,
                                    EmployeeRepository employeeRepository,
                                    EntityManager em) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
        this.em = em;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal("10000")));
//
//        employeeRepository.insert(new PartTimeEmployee("Jack", new BigDecimal("50")));
//
//       // log.info("list ---> {}", employeeRepository.retrieveAllEmployees());
//        log.info("list ---> {}", employeeRepository.retrieveAllPartTimeEmployees());
//        log.info("list ---> {}", employeeRepository.retrieveAllFullTimeEmployees());

//        Student byId = studentRepository.findById(2000L);
//
//        byId.setName("updated");
//        studentRepository.save(byId);

    }

}
