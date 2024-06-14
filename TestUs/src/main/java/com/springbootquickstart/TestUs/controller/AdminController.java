package com.springbootquickstart.TestUs.controller;


import com.springbootquickstart.TestUs.dto.CourseDto;
import com.springbootquickstart.TestUs.dto.UserRegisteredDto;
import com.springbootquickstart.TestUs.model.*;
import com.springbootquickstart.TestUs.repository.MyUserRepository;
import com.springbootquickstart.TestUs.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;


    @Autowired
    MyUserDetailService userService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseStudentService courseStudentService;




    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Optional<MyUser> myUser = userService.findByEmail(user.getUsername());
        if (myUser.isPresent()) {
            return myUser.get().getEmail();
        }else{
            throw new UsernameNotFoundException(user.getUsername());
        }
    }



    /* admin main menu page */
    @GetMapping("")
    public String adminMenu(Model model) {

        final String[] menuItemsText = {"View Courses", "View Users" , "Logout"};
        final String[] menuItemsUrl = {"view-courses", "view-users", "logout"};

        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < menuItemsText.length; i++) {
            if(menuItemsText[i].equals("Logout")) {
                buttons.add(new Button(menuItemsText[i],  "logout"));
                continue;
            }
            buttons.add(new Button(menuItemsText[i],  "admin/" + menuItemsUrl[i]));
        }

        model.addAttribute("buttons", buttons);

        //title of the menu
        model.addAttribute("menuTitle", "Admin Menu");

        return "mainMenu";
    }

    @GetMapping("/view-courses")
    public String viewCourses(Model model) {
        List<Course> courseList = new ArrayList<>(courseService.findAll());
        model.addAttribute("courseList", courseList);
        model.addAttribute("allTeacher",teacherService.findAll());
        return "admin/courseViewAdmin";
    }


    @GetMapping("/courseDetails")
    public String viewCourse(@RequestParam("courseId") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("selectedCourse", course);
        model.addAttribute("allTeacher", teacherService.findAll());
        model.addAttribute("courseStudents", courseStudentService.findByCourse(course));
        return "/admin/courseDetailsAdmin";
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    public String addCourse(@ModelAttribute("course") CourseDto courseDto,Model model) {
        model.addAttribute("allTeacher",teacherService.findAll());
        courseService.save(courseDto);
        return "redirect:/admin/view-courses";
    }

    @PostMapping("/update-course")
    public String updateCourse(
            @RequestParam("courseId") Long courseId,
            @RequestParam("courseName") String courseName,
            @RequestParam("courseTeacher") Long teacherId,
            @RequestParam("courseDescription") String courseDescription,
            Model model) {

        Course course = courseService.findById(courseId);
        course.setName(courseName);
        course.setDescription(courseDescription);
        Teacher teacher = teacherService.findByTeacherId(teacherId);
        course.setTeacher(teacher);
        courseService.update(course);
        return "redirect:/admin/view-courses";
    }


    @GetMapping("/view-users")
    public String viewUsers( Model model) {
        List<MyUser> usersList = new ArrayList<>(userService.findAll());
        MyUser myUser = userService.returnMyUser();
        usersList = usersList.stream().filter(user -> !user.getId().equals(myUser.getId())).collect(Collectors.toList());


        model.addAttribute("usersList", usersList);

        return "/admin/viewUsers";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam("selectedCourseStudentId") Long courseStudentId,
                                               @RequestParam("status") String status, Model model) {


        // Call the changeStatus method from StudentCourseService
       courseStudentService.changeStatus(courseStudentId, status);

       return "redirect:/admin/courseDetails?courseId=" + courseStudentService.findById(courseStudentId).getCourse().getId();
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Long userId, @RequestParam Boolean isAccountLocked) {
        // Fetch the user by ID
        Optional<MyUser> user = userService.findById(userId);
        if (user.isPresent()) {
            // Update the user status
            user.get().setAccountLocked(isAccountLocked);
            userService.save(user.get()); // Save the updated user
        }
        // Redirect back to the users list page
        return "redirect:/admin/view-users";
    }

}