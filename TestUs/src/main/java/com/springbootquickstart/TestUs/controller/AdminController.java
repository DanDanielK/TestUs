package com.springbootquickstart.TestUs.controller;


import com.springbootquickstart.TestUs.dto.UserRegisteredDto;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Role;
import com.springbootquickstart.TestUs.repository.MyUserRepository;
import com.springbootquickstart.TestUs.service.Button;
import com.springbootquickstart.TestUs.service.CourseService;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;


    @Autowired
    MyUserDetailService userService;




    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Optional<MyUser> myUser = userService.findByUsername(user.getUsername());
        if (myUser.isPresent()) {
            return myUser.get().getUsername();
        }else{
            throw new UsernameNotFoundException(user.getUsername());
        }
//        MyUser users = userRepository.findByUsername(user.getUsername());
//        return users.getName();
    }


    @GetMapping()
    public String displayAdminDashboard(Model model){
        String user= returnUsername();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<MyUser> myUser = userService.findByUsername(auth.getName());
        model.addAttribute("courseList", courseService.findAll());

        model.addAttribute("userDetails", user);
        return "admin/profile";
    }

    public String getCourseList(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<MyUser> myUser = userService.findByUsername(auth.getName());
        if (myUser.isPresent()) {
            model.addAttribute("courseList", courseService.findAll());
            return "admin/course";
        }else{
            return "redirect:/login";
        }
    }

    /* admin main menu page */
    @GetMapping("")
    public String adminMenu(Model model) {

        final String[] menuItemsText = {"Add New Coordinator", "View Coordinators","View Students" , "Logout"};
        final String[] menuItemsUrl = {"add-coordinator", "view-coordinators","view-students", "logout"};

        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < menuItemsText.length; i++) {
            buttons.add(new Button(menuItemsText[i],  "admin/" + menuItemsUrl[i]));
        }

        model.addAttribute("buttons", buttons);

        //title of the menu
        model.addAttribute("menuTitle", "Admin Menu");

        return "mainMenu";
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


    @GetMapping("/view-{role:(?:students|coordinators)}")
    public String viewUsers(@PathVariable("role") String role, Model model) {

        List<MyUser> usersList = new ArrayList<>();


        //DODO: get users by role
        // Optional<User> users = Optional.empty();

        // if (role.equals("coordinators")) {

        //     //users = userRepository.findByRolesName("coordinator");
        // } else {
        //     //users = userRepository.findByRolesName("student");
        // }

        // //model.addAttribute("users", users);


        MyUser user1 = new MyUser((int) 209355234, "ssss@gggm.com", "password", "John", "Doe", Role.ADMIN);


        usersList.add(user1); //remove

        model.addAttribute("usersList", usersList);

        return "viewUsers";
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


    //change to get username instead of id
    @GetMapping("/view-{role:(?:students|coordinators)}/edit")
    public String editeUser(@PathVariable("role") String role, @RequestParam("username") String username, RedirectAttributes r, Model model) {

        try {

            //handle href link
            model.addAttribute("role", role);

            //get user by id
            MyUser user = userService.findByUsername(username).get();

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

}