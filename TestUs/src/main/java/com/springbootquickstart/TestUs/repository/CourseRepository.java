package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.Course;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @NotNull
    List<Course> findAll();

}