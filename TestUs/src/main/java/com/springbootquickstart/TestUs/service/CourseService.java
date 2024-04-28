package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.dto.CourseDto;
import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.repository.CourseRepository;
import com.springbootquickstart.TestUs.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherService teacherService;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course save(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getCourseName());
        course.setDescription(courseDto.getCourseDescription());
        course.setTeacher(teacherService.findByEmail(courseDto.getTeacherEmail())) ;

        return courseRepository.save(course);
    }
}
