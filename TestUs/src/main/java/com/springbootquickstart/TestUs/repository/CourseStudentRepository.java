package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.CourseStudent;
import com.springbootquickstart.TestUs.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
    List<CourseStudent> findByCourse(Course course);
    List<CourseStudent> findByStudent(Student student);
    Optional<CourseStudent> findById(Long id);
    Optional<CourseStudent> findByCourseAndStudent(Course course, Student student);
}
