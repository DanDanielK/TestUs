package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.Student;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @NotNull
    List<Course> findAll();

    @Query("SELECT c FROM Course c JOIN c.tests t WHERE t.testId = :testId")
    Course findCourseByTestId(@Param("testId") long testId);

    List<Course> findCoursesByTeacherId(long teacherId);

}
