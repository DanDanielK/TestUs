package com.springbootquickstart.TestUs.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbootquickstart.TestUs.dto.RegisterDto;
import com.springbootquickstart.TestUs.user.UserService;


@Controller
public class RegisterController {

    @Autowired
    private UserService userService;
   

    @GetMapping("/register")
    public String register() {
        return "register";

    }

    @PostMapping(path = "/register" , consumes = "application/x-www-form-urlencoded")
    public String registerUser(RegisterDto registerDto,RedirectAttributes redirectAttributes) {
        
        try {
            userService.registerUser(registerDto);

            //if was successful let user know
            // Add a flash attribute to indicate success
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful!");
        
            // Redirect to the register page
            return "redirect:/register";
           
        } catch (Exception e) {
            return null;
            //TOdO: handle error UI
        }

        
    }
    
}
