package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.entity.Course;
import com.raminq.jpa_hibernate.entity.Passport;
import com.raminq.jpa_hibernate.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;


@Repository
@Transactional
public class StudentRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final EntityManager em;

    @Autowired
    public StudentRepository(EntityManager em) {
        this.em = em;
    }

    public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    //create or update
    public Student save(Student Student) {
        if (Student.getId() == null) {
            em.persist(Student);
        } else {
            em.merge(Student);
        }
        return Student;
    }

    public void deleteById(Long id) {
        Student Student = findById(id);
        em.remove(Student);
    }

    //-------------------------------

    public void saveStudentWithPassport() {
        Passport newPassport = new Passport("X898343");
        em.persist(newPassport);

        Student newStudent = new Student("Mike");
        newStudent.setPassport(newPassport);

        em.persist(newStudent);
    }

    public void insertStudentAndCourse() {
        Student newStudent = new Student("Sam");

//        List<Course> newCourses = Arrays.asList(
//                new Course("Algebra"),
//                new Course("Mathematics"));

        newStudent.addCourse( new Course("Algebra"));
        newStudent.addCourse( new Course("Mathematics"));

        em.persist(newStudent);
    }
}