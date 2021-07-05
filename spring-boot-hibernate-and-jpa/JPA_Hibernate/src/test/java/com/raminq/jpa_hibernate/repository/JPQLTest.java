package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.JpaHibernateApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class JPQLTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private EntityManager em;
    private CourseRepository courseRepository;

    @Autowired
    public JPQLTest(EntityManager entityManager, CourseRepository courseRepository) {
        em = entityManager;
        this.courseRepository = courseRepository;
    }

    @Test
    void list_basic() {
        log.info("list -->{}", courseRepository.jpql_list());
    }

    @Test
    void list_repository_typed() {
        log.info("list typed -->{}", courseRepository.jpql_list_typed());
    }

    @Test
    void jpql_where() {
        log.info("jpql_where -->{}", courseRepository.jpql_where("steps"));
    }

    @Test
    void jpql_courses_without_student() {
        log.info("jpql_courses_without_student -->{}", courseRepository.jpql_courses_without_student());
    }

    @Test
    void jpql_courses_withAtLeast2_student_orderBy() {
        log.info("jpql_courses_withAtLeast2_student_orderBy -->{}", courseRepository.jpql_courses_withAtLeast2_student_orderBy());
    }

    @Test
    void jpql_courses_like() {
        log.info("jpql_courses_like -->{}", courseRepository.jpql_courses_like("Co"));
    }

}

