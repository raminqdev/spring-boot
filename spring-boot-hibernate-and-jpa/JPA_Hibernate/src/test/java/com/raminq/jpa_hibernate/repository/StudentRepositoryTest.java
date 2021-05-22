package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.JpaHibernateApplication;
import com.raminq.jpa_hibernate.entity.Passport;
import com.raminq.jpa_hibernate.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class StudentRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private StudentRepository studentRepository;
    @Autowired
    private EntityManager em;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //Session and Session Factory
    //EntityManger and Persistence Context
    //Transaction

    @Test
    @Transactional //Persistence Context
    public void someTest() {
        //Database Operation 1 -Retrieve student
        Student student = em.find(Student.class, 2000L);
        //--- Persistence Context(student)

        //Database Operation 2 -Retrieve passport
        Passport passport = student.getPassport();
        //--- Persistence Context(student, passport)

        //Database Operation 3 -update student
        passport.setNumber("EX498394");
        //--- Persistence Context(student, passport++)

        //Database Operation 4 -update passport
        student.setName("Sally - updated");
        //--- Persistence Context(student++, passport++)
    }

    @Test
    @Transactional
    void retrieveStudentAndPassportDetails() {
        Student student = em.find(Student.class, 2000L);
        log.info("student  -> {}", student);
        log.info("passport  -> {}", student.getPassport());

        Student byId = studentRepository.findById(2000L);
        log.info("studentRepository  -> {} {} ", byId, byId.getPassport());
    }

    @Test
    @Transactional
    void retrievePassportAndStudentDetails() {
        Passport passport = em.find(Passport.class, 4000L);
        log.info("passport  -> {}", passport);
        log.info("student  -> {}", passport.getStudent());
    }

    @Test
    @Transactional
    void to_do() {
        Passport passport = em.find(Passport.class, 4000L);

        Student newStudent = new Student("Mike");
        newStudent.setPassport(passport);
    }

    @Test
    @Transactional
    void retrieveStudentAndCourse() {
        Student student = em.find(Student.class, 2001L);
        log.info("student  -> {}", student);
        log.info("student.getCourses  -> {}", student.getCourses());
    }
}