package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.entity.Course;
import com.raminq.jpa_hibernate.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
@Transactional
public class CourseRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final EntityManager em;

    @Autowired
    public CourseRepository(EntityManager em) {
        this.em = em;
    }

    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    //create or update
    public Course save(Course course) {
        if (course.getId() == null) {
            em.persist(course);
        } else {
            em.merge(course);
        }
        return course;
    }

    public void deleteById(Long id) {
        Course course = findById(id);
        em.remove(course);
    }

    public void playWithEntityManager() {
        Course course = new Course("Web Services in 100 steps");
        em.persist(course);
        Course course2 = new Course("Angular Js in 20 steps");
        em.persist(course2);

        em.flush();

        // instead of em.detach(course2);  and  em.detach(course2);
        em.clear();

        course.setName("Web Services in 100 steps - updated");
        em.flush();

        course2.setName("Angular Js in 20 steps - updated");
        em.flush();

        //Refreshing
        Course course3 = new Course("React Js in 40 steps");
        em.persist(course3);
        em.flush();

        course3.setName("React Js in 40 steps - updated");
        em.refresh(course3);

        em.flush();
    }


    /*---------------- JPQL -----------------
     * in jpql we query using entities
     */

    //namedQuery
    public List<Course> jpql_list() {
        Query query = em.createNamedQuery("query_get_all_courses");
        List resultList = query.getResultList();
        return resultList;
    }

    public List<Course> jpql_list_typed() {
        TypedQuery<Course> typedQuery = em.createNamedQuery("query_get_all_courses", Course.class);
        List<Course> resultList = typedQuery.getResultList();
        return resultList;
    }

    //Query
    public List<Course> jpql_where(String courseName) {
        TypedQuery<Course> typedQuery =
                em.createQuery("Select c From Course c where c.name like '%" + courseName + "'", Course.class);
        List<Course> resultList = typedQuery.getResultList();
        return resultList;
    }

    public List<Course> jpql_courses_like(String name) {
        return em.createQuery("Select c From Course c where c.name like '%" + name + "%'",
                Course.class).getResultList();
    }

    public List<Course> jpql_courses_without_student() {
        return em.createQuery("Select c From Course c where c.students is empty", Course.class).getResultList();

    }

    public List<Course> jpql_courses_withAtLeast2_student_orderBy() {
        return em.createQuery("Select c From Course c where size(c.students) >= 2 order by c.name", Course.class).getResultList();
    }




    /*---------------- Native query -----------------
     * we use native query where jpa doesn't support (mass update ,...)
     * */

    public List<Course> native_query_list() {
        Query query = em.createNativeQuery("SELECT * FROM course", Course.class);
        List resultList = query.getResultList();
        return resultList;
    }

    public List<Course> native_query_list_where(String nameOfCourse) {
        Query query = em.createNativeQuery("SELECT * FROM course WHERE name=:nameParameter", Course.class);
        query.setParameter("nameParameter", nameOfCourse);
        List resultList = query.getResultList();
        return resultList;
    }

    public int native_query_updateAllRows() {
        Query query = em.createNativeQuery("UPDATE course SET last_Updated_Date = sysdate()");
        return query.executeUpdate();
    }

    //--------------------------------------------

    public void addHardCodedReviewsForCourse() {
        Course course = findById(1000L);
        log.info("course.getReviews() -> {}", course.getReviews());

        Review review1 = new Review("5", "Great hands-on stuff");
        Review review2 = new Review("5", "Hatsoff");

        course.addReview(review1);
        review1.setCourse(course); //review owns relationship

        course.addReview(review2);
        review2.setCourse(course);

        em.persist(review1);
        em.persist(review2);
    }

    public void addReviewsForCourse(Long courseId, List<Review> reviews) {
        Course course = findById(courseId);
        log.info("course.getReviews() -> {}", course.getReviews());

        for (Review review : reviews) {
            course.addReview(review);
            review.setCourse(course); //review owns relationship
            em.persist(review);
        }
    }
}
