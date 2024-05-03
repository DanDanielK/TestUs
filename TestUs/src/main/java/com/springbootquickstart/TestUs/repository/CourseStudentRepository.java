package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
}
