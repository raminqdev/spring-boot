package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.JpaHibernateApplication;
import com.raminq.jpa_hibernate.entity.Course;
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
class JPQL_joinTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private EntityManager em;
    private CourseRepository courseRepository;

    @Autowired
    public JPQL_joinTest(EntityManager entityManager, CourseRepository courseRepository) {
        em = entityManager;
        this.courseRepository = courseRepository;
    }


    @Test
    void join() {
        List<Object[]> resultList = em.createQuery("Select c, s From Course c JOIN c.students s").getResultList();
        log.info("join --> {}", resultList.size());

        for (Object[] result : resultList) {
            log.info("Course {} Student{}", result[0], result[1]);
        }
    }
    /*
     select
        course0_.id as id1_0_0_,
        student2_.id as id1_4_1_,
        course0_.created_date as created_2_0_0_,
        course0_.last_updated_date as last_upd3_0_0_,
        course0_.name as name4_0_0_,
        student2_.name as name2_4_1_,
        student2_.passport_id as passport3_4_1_
    from
        course course0_
    inner join
        student_course students1_
            on course0_.id=students1_.course_id
    inner join
        student student2_
            on students1_.student_id=student2_.id
     */

    @Test
    void left_join() {
        List<Object[]> resultList = em.createQuery("Select c, s From Course c LEFT JOIN c.students s").getResultList();
        log.info("join --> {}", resultList.size());

        for (Object[] result : resultList) {
            log.info("Course {} Student{}", result[0], result[1]);
        }
    }
    /*
    select
        course0_.id as id1_0_0_,
        student2_.id as id1_4_1_,
        course0_.created_date as created_2_0_0_,
        course0_.last_updated_date as last_upd3_0_0_,
        course0_.name as name4_0_0_,
        student2_.name as name2_4_1_,
        student2_.passport_id as passport3_4_1_
    from
        course course0_
    left outer join
        student_course students1_
            on course0_.id=students1_.course_id
    left outer join
        student student2_
            on students1_.student_id=student2_.id
     */

    @Test
    void cross_join() {
        List<Object[]> resultList = em.createQuery("Select c, s From Course c, Student s").getResultList();
        log.info("join --> {}", resultList.size());

        for (Object[] result : resultList) {
            log.info("Course {} Student{}", result[0], result[1]);
        }
    }
    /*
      select
        course0_.id as id1_0_0_,
        student1_.id as id1_4_1_,
        course0_.created_date as created_2_0_0_,
        course0_.last_updated_date as last_upd3_0_0_,
        course0_.name as name4_0_0_,
        student1_.name as name2_4_1_,
        student1_.passport_id as passport3_4_1_
    from
        course course0_ cross
    join
        student student1_
     */

}

