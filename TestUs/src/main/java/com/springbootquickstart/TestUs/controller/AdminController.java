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
//        MyUser users = userRepository.findByUsername(user.getUsername());
//        return users.getName();
    }


//    @GetMapping()
//    public String displayAdminDashboard(Model model){
//        String user= returnUsername();
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Optional<MyUser> myUser = userService.findByUsername(auth.getName());
//        model.addAttribute("courseList", courseService.findAll());
//
//        model.addAttribute("userDetails", user);
//        return "admin/profile";
//    }


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



    @GetMapping("/add-coordinator")
    public String addCoordinator() {
        return "addCoordinator";
    }

    @PostMapping(path = "/add-coordinator" , consumes = "application/x-www-form-urlencoded")
    public String addCoordinatorPost(UserRegisteredDto registerDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            //change the role to coordinator - ONLY ADMIN CAN ADD COORDINATORS!!!
            registerDto.setRole("coordinator");

            userService.save(registerDto);

            //add a flash attribute to indicate success
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful!");

            //redirect to the same page to clear the form
            return "redirect:" + request.getRequestURI();

        } catch (Exception e) {
            return null; //DOTO handle error UI
        }

    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    public String addCourse(@ModelAttribute("course") CourseDto courseDto,Model model) {
        model.addAttribute("allTeacher",teacherService.findAll());
        courseService.save(courseDto);
        return "redirect:/admin/view-courses";
    }

//    @RequestMapping(value = "/courseDetails", method = RequestMethod.POST)
//    public String courseDetails(@ModelAttribute("courseId") int courseId) {
//
//        courseService.save(courseDto);
//        return "redirect:/admin/view-courses";
//    }

    @GetMapping("/courseDetails")
    public String viewCourse(@RequestParam("courseId") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("selectedCourse", course);
        model.addAttribute("allTeacher", teacherService.findAll());
        model.addAttribute("courseStudents", courseStudentService.findByCourse(course));
        return "/admin/courseDetailsAdmin";
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

//need to chane to deactivate
    @GetMapping("/view-{role:(?:students|coordinators)}/delete")
    public String deleteUser(@PathVariable("role") String role, @RequestParam("id") Long id, RedirectAttributes r, Model model) {

        try {

            //handle href link
            model.addAttribute("role", role);

            //delete user
          // userService.deleteUser(id);

            //indicate success
            r.addFlashAttribute("successMessage", " user with ID " + id + " has been deleted.");

        } catch (Exception e) {
            r.addFlashAttribute("failMessage", e.getMessage());
        }

        //return to the same page
        return "redirect:/admin/view-" + role;
    }


    @GetMapping("/view-{role:(?:students|coordinators)}/edit")
    public String editeUser(@PathVariable("role") String role, @RequestParam("id") Long id, RedirectAttributes r, Model model) {

        try {

            //handle href link
            model.addAttribute("role", role);

            //get user by id
           MyUser user = userService.getUser(id);

            //send user info to the edit page
            model.addAttribute("user", user);


        } catch (Exception e) {
            r.addFlashAttribute("failMessage", e.getMessage());
            //return the same page
            return "redirect:/admin/view-" + role;
        }

        //return the edit page
        return "redirect:/admin/view-" + role + "/edit";
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