package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.JpaHibernateApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class NativeTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private EntityManager em;
    private CourseRepository courseRepository;

    @Autowired
    public NativeTest(EntityManager entityManager, CourseRepository courseRepository) {
        em = entityManager;
        this.courseRepository = courseRepository;
    }

    @Test
    void native_queries_basic() {
        log.info("native_query_list -->{}", courseRepository.native_query_list());
    }

    @Test
    void native_query_list_where() {
        log.info("native_query_list -->{}", courseRepository.native_query_list_where("Algorithms"));
    }

    @Test
    void native_query_updateAllRows() {
        log.info("native_query_updateAllRows -->{}", courseRepository.native_query_updateAllRows());
    }


}

