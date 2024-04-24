package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.repository.MyUserRepository;
import com.springbootquickstart.TestUs.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CourseController {

    @Autowired
    private MyUserRepository userRepository;

    @Autowired
    private CourseService courseService;


    @GetMapping("/admin/course")
    public String getCourseList(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<MyUser> myUser = userRepository.findByUsername(auth.getName());
        if (myUser.isPresent()) {
            model.addAttribute("courseList", courseService.findAll());
            return "admin/course";
    }else{
        return "redirect:/login";
    }
    }
}
