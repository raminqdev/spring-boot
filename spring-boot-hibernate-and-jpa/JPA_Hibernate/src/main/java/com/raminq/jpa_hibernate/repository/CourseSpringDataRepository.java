package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {

    Collection<Course> findByName(String name);

    Collection<Course> findByNameAndId(String name, Long id);

    int countByName(String name);

    Collection<Course> findByNameOrderByName(String name);

    @Query("SELECT c FROM Course c WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', ?1,'%') )")
    Collection<Course> findCoursesLike(String name);

    @Query(value = "SELECT * FROM Course c WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', ?1,'%') )", nativeQuery = true)
    Collection<Course> findCoursesLikenNativeQuery(String name);

    @Query(name = "query_get_all_courses")
    Collection<Course> findCoursesNamedQuery();
}
