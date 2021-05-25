package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.JpaHibernateApplication;
import com.raminq.jpa_hibernate.entity.Course;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class CourserSpringDataRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private CourseSpringDataRepository courseSpringDataRepository;
    private EntityManager em;

    @Autowired
    public CourserSpringDataRepositoryTest(CourseSpringDataRepository courseSpringDataRepository, EntityManager em) {
        this.courseSpringDataRepository = courseSpringDataRepository;
        this.em = em;
    }

    @Test
    public void findById_coursePresent() {
        Optional<Course> optionalCourse = courseSpringDataRepository.findById(1000L);
        Assert.assertTrue(optionalCourse.isPresent());
    }

    @Test
    public void findById_course_Not_Present() {
        Optional<Course> optionalCourse = courseSpringDataRepository.findById(10099L);
        Assert.assertFalse(optionalCourse.isPresent());
    }

    @Test
    public void playAround() {
        Course newCourse = new Course("Microservices");

        courseSpringDataRepository.save(newCourse);

        newCourse.setName("Microservices- updated");

        courseSpringDataRepository.save(newCourse);
    }

    @Test
    public void sort() {
        //sort by name desc
        Sort sort = Sort.by(Sort.Direction.DESC, "name", "id");
        log.info("{}", courseSpringDataRepository.findAll(sort));
    }

    @Test
    public void paging() {
        Sort sort = Sort.by(Sort.Direction.DESC, "name", "id");

        PageRequest pageRequest = PageRequest.of(0, 3, sort);
        Page<Course> firstPage = courseSpringDataRepository.findAll(pageRequest);

        log.info("firstPage.getContent --------> {}", firstPage.getContent());

        Pageable secondPageable = firstPage.nextPageable();
        Page<Course> secondPage = courseSpringDataRepository.findAll(secondPageable);
        log.info("secondPage.getContent --------> {}", secondPage.getContent());
    }

    @Test
    public void findCoursesLike() {
        log.info("findByName---> {}", courseSpringDataRepository.findCoursesLike("Dummy"));
        log.info("findCoursesLikenNativeQuery---> {}", courseSpringDataRepository.findCoursesLikenNativeQuery("Dummy"));
    }

}
