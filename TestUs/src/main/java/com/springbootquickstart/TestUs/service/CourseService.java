package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.dto.CourseDto;
import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.repository.CourseRepository;
import com.springbootquickstart.TestUs.repository.TeacherRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springbootquickstart.TestUs.model.CourseStudent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course save(@NotNull CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getCourseName());
        course.setDescription(courseDto.getCourseDescription());
        course.setTeacher(teacherService.findByEmail(courseDto.getTeacherEmail())) ;
        //course.addStudent(studentService.findByEmail("student@testus.com"));

        return courseRepository.save(course);
    }
    public List<Course> findCoursesByTeacherId(int teacherId) {
        return courseRepository.findCoursesByTeacherId(teacherId);
    }



//    public void enrollStudentToCourse(Student student, Course course) {
//        course.addStudent(student);
//        student.addCourse(course);
//        studentService.save(student);
//    }
//
//    public void unenrollStudentFromCourse(Student student, Course course) {
//        student.removeCourse(course);
//        studentService.save(student);
//    }

    public void save(Course course) {
        courseRepository.save(course);
    }

}
