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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class Criteria_Query_Test {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private EntityManager em;
    private CourseRepository courseRepository;

    @Autowired
    public Criteria_Query_Test(EntityManager entityManager, CourseRepository courseRepository) {
        em = entityManager;
        this.courseRepository = courseRepository;
    }

    @Test
    void all_courses() {
        //"Select c From Course c"
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(courseRoot));

        List<Course> resultList = typedQuery.getResultList();
        log.info("Criteria --> {}", resultList);
    }
    /*
       select
        course0_.id as id1_0_,
        course0_.created_date as created_2_0_,
        course0_.last_updated_date as last_upd3_0_,
        course0_.name as name4_0_
    from
        course course0_
     */


    @Test
    void all_courses_having_100steps() {
        //"Select c From Course c where name like '%100 Steps'"
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        Predicate like100Steps = criteriaBuilder.like(courseRoot.get("name"), "%100 Steps");
        criteriaQuery.where(like100Steps);

        TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(courseRoot));

        List<Course> resultList = typedQuery.getResultList();
        log.info("Criteria --> {}", resultList);
    }
    /*
       select
        course0_.id as id1_0_,
        course0_.created_date as created_2_0_,
        course0_.last_updated_date as last_upd3_0_,
        course0_.name as name4_0_
    from
        course course0_
    where
        course0_.name like ?
     */

    @Test
    void all_courses_without_student() {
        //"Select c From Course c where c.students is empty'"
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        Predicate studentsIsEmpty = criteriaBuilder.isEmpty(courseRoot.get("students"));
        criteriaQuery.where(studentsIsEmpty);

        TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(courseRoot));

        List<Course> resultList = typedQuery.getResultList();
        log.info("Criteria --> {}", resultList);
    }
    /*
      select
        course0_.id as id1_0_,
        course0_.created_date as created_2_0_,
        course0_.last_updated_date as last_upd3_0_,
        course0_.name as name4_0_
    from
        course course0_
    where
        not (exists (select
            student2_.id
        from
            student_course students1_,
            student student2_
        where
            course0_.id=students1_.course_id
            and students1_.student_id=student2_.id))
     */

    @Test
    void join() {
        //"Select c From Course c join c.students s'"
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        Join<Object, Object> join = courseRoot.join("students");

        TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(courseRoot));

        List<Course> resultList = typedQuery.getResultList();
        log.info("Criteria --> {}", resultList);
    }
    /*
       select
        course0_.id as id1_0_,
        course0_.created_date as created_2_0_,
        course0_.last_updated_date as last_upd3_0_,
        course0_.name as name4_0_
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
        //"Select c From Course c left join c.students s'"
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = criteriaQuery.from(Course.class);

        Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT);

        TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(courseRoot));

        List<Course> resultList = typedQuery.getResultList();
        log.info("Criteria --> {}", resultList);
    }
    /*
       select
        course0_.id as id1_0_,
        course0_.created_date as created_2_0_,
        course0_.last_updated_date as last_upd3_0_,
        course0_.name as name4_0_
    from
        course course0_
    left outer join
        student_course students1_
            on course0_.id=students1_.course_id
    left outer join
        student student2_
            on students1_.student_id=student2_.id
     */
}

