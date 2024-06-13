package com.springbootquickstart.TestUs.controller;


import com.springbootquickstart.TestUs.dto.UserRegisteredDto;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private  MyUserDetailService userService;


    @ModelAttribute("user")
    public UserRegisteredDto userRegistrationDto() {
        return new UserRegisteredDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegisteredDto registrationDto) {
        userService.save(registrationDto);
        return "redirect:/login";
    }


}
