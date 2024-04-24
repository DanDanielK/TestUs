package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
