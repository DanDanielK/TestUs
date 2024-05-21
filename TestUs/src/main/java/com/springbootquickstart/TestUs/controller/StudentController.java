package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.repository.MyUserRepository;
import com.springbootquickstart.TestUs.service.Button;
import com.springbootquickstart.TestUs.service.CourseService;
import com.springbootquickstart.TestUs.service.CourseStudentService;
import com.springbootquickstart.TestUs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {




    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseStudentService courseStudentService;


//    @GetMapping()
//    public String displayStudentDashboard(Model model){
//        String user= returnUsername();
//        model.addAttribute("userDetails", user);
//        return "student/profile";
//    }







    /* student main menu page */
    @GetMapping("")
    public String studentMenu(Model model) {

        //student menu page options
        final String[] menuItemsText = {"view courses", "Review Past Tests", "Logout"};
        final String[] menuItemsUrl = {"view-courses", "review-past-tests", "logout"};

        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < menuItemsText.length; i++) {
            buttons.add(new Button(menuItemsText[i],  "student/" + menuItemsUrl[i]));
        }

        model.addAttribute("buttons", buttons);

        //title of the menu
        model.addAttribute("menuTitle", "Student Menu");

        return "mainMenu";
    }

    @GetMapping("/view-courses")
    public String viewCourses(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student= studentService.findByEmail(auth.getName());
        List<Course> courseList=new ArrayList<>(courseStudentService.getCoursesByStudent(student));
    List<Course>coursesNotEnrolled=courseStudentService.findCoursesNotEnrolledByStudentId(student);
        model.addAttribute("courseStudentList",courseStudentService.findByStudent(student));
        model.addAttribute("coursesNotEnrolled",coursesNotEnrolled);
        return "student/courseView";
    }

    @RequestMapping(value="/addCourse", method= RequestMethod.POST)
    public String addCourse(@RequestParam("courseId") int courseId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());
        Course course = courseService.findById((long)courseId);
        courseStudentService.addStudentToCourse(course,student);
        return "redirect:/student/view-courses";
    }



}