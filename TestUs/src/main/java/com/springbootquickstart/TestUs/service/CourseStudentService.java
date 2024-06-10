package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.CourseStudent;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.repository.CourseStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseStudentService {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseStudentRepository courseStudentRepository;

    public void addStudentToCourse(Course course, Student student) {
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setStudent(student);
        courseStudent.setCourse(course);
        courseStudent.setSignupStatus(CourseStudent.SignupStatus.WAITING);
        this.save(courseStudent);

    }

    public void removeStudentFromCourse(Course course, Student student) {
        Iterator<CourseStudent> iterator = course.getCourseStudents().iterator();
        while (iterator.hasNext()) {
            CourseStudent courseStudent = iterator.next();
            if (courseStudent.getStudent().equals(student)) {
                iterator.remove();
                courseStudent.getStudent().getCourseStudents().remove(courseStudent);
                courseStudent.setCourse(null);
                courseStudent.setStudent(null);
            }
        }
    }

    public void save(CourseStudent courseStudent) {
        courseStudentRepository.save(courseStudent);
        courseService.save(courseStudent.getCourse());
        studentService.save(courseStudent.getStudent());
    }

    public List<Course> getCoursesByStudent(Student student) {
        return student.getCourseStudents().stream()
                .map(CourseStudent::getCourse)
                .collect(Collectors.toList());
    }
    public List<Student> getStudentsByCourse(Course course) {
        return course.getCourseStudents().stream()
                .map(CourseStudent::getStudent)
                .collect(Collectors.toList());
    }

    public List<Course> findCoursesNotEnrolledByStudentId(Student student) {
        List<Course> allCourses = courseService.findAll();
        List<Course> enrolledCourses = student.getCourseStudents().stream()
                .map(CourseStudent::getCourse)
                .collect(Collectors.toList());

        return allCourses.stream()
                .filter(course -> !enrolledCourses.contains(course))
                .collect(Collectors.toList());
    }


    public List<CourseStudent> findAll() {
        return  courseStudentRepository.findAll();
    }

    public List <CourseStudent> findByCourse(Course course){
        return courseStudentRepository.findByCourse(course);
    }
    public List <CourseStudent> findByStudent(Student student){
        return courseStudentRepository.findByStudent(student);
    }
    public CourseStudent findByCourseAndStudent(Course course, Student student){
        return courseStudentRepository.findByCourseAndStudent(course, student).orElse(null);
    }

    public void changeStatus(Course course,Student student, String status){
        CourseStudent courseStudent = findByCourseAndStudent(course, student);
        courseStudent.setSignupStatus(CourseStudent.SignupStatus.valueOf(status));
        save(courseStudent);
    }

    public void changeStatus(Long courseStudentId, String status){
        CourseStudent courseStudent = findById(courseStudentId);
        courseStudent.setSignupStatus(CourseStudent.SignupStatus.valueOf(status));
        save(courseStudent);
    }

    public CourseStudent findById(Long id){
        return courseStudentRepository.findById(id).orElse(null);
    }


    

}
