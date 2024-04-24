package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.repository.MyUserRepository;
import com.springbootquickstart.TestUs.service.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private MyUserRepository userRepository;


    @GetMapping()
    public String displayStudentDashboard(Model model){
        String user= returnUsername();
        model.addAttribute("userDetails", user);
        return "student/profile";
    }




    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Optional<MyUser> myUser = userRepository.findByUsername(user.getUsername());
        if (myUser.isPresent()) {
            return myUser.get().getUsername();
        }else{
            throw new UsernameNotFoundException(user.getUsername());
        }
       // MyUser users = userRepository.findByUsername(user.getUsername());
       // return users.getName();
    }


    /* student main menu page */
    @GetMapping("")
    public String studentMenu(Model model) {

        //student menu page options
        final String[] menuItemsText = {"Take new Test", "Review Past Tests", "Logout"};
        final String[] menuItemsUrl = {"take-test", "review-past-tests", "logout"};

        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < menuItemsText.length; i++) {
            buttons.add(new Button(menuItemsText[i],  "student/" + menuItemsUrl[i]));
        }

        model.addAttribute("buttons", buttons);

        //title of the menu
        model.addAttribute("menuTitle", "Student Menu");

        return "mainMenu";
    }



}