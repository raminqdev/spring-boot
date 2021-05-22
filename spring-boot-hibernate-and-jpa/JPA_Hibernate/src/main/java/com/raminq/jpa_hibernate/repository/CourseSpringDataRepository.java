package com.raminq.jpa_hibernate.repository;

import com.raminq.jpa_hibernate.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
}
