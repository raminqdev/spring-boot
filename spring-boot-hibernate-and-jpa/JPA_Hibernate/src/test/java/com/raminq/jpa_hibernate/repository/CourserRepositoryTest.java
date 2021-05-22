package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.JpaHibernateApplication;
import com.raminq.jpa_hibernate.entity.Course;
import com.raminq.jpa_hibernate.entity.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class CourserRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private CourseRepository courseRepository;
    private EntityManager em;

    @Autowired
    public CourserRepositoryTest(CourseRepository courseRepository, EntityManager em) {
        this.courseRepository = courseRepository;
        this.em = em;
    }


    @Test
    void findById_basic() {
        Assertions.assertEquals("JPA Course", courseRepository.findById(1000L).getName());
    }

    @Test
    @DirtiesContext
    void deleteById_basic() {
        courseRepository.deleteById(1002L);
        Assertions.assertNull(courseRepository.findById(1002L));
    }


    @Test
    @DirtiesContext
    void save_basic() {
        Course course = courseRepository.findById(1000L);
        Assertions.assertEquals("JPA Course", course.getName());

        course.setName("JPA Course - updated");

        Course updatedCourse = courseRepository.save(course);
        Assertions.assertEquals("JPA Course - updated", updatedCourse.getName());
    }

    @Test
    @DirtiesContext
    void playWithEntityManager() {
        courseRepository.playWithEntityManager();
    }


    @Test
    @DirtiesContext
    void fetchType_test() {
        log.info("em.find() --> {}",em.find(Review.class, 5000L).getCourse());
        log.info("courseRepository.findById --> {}", courseRepository.findById(1001L).getReviews());
    }


}
