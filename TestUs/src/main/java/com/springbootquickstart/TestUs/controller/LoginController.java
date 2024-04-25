package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.dto.UserLoginDto;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private MyUserDetailService userDetailService;

    @ModelAttribute("user")
    public UserLoginDto userLoginDto() {
        return new UserLoginDto();
    }

    @GetMapping
    public String login() {
        return "home";
    }

    @PostMapping
    public void loginUser(@ModelAttribute("user") UserLoginDto userLoginDto) {
        userDetailService.loadUserByUsername(userLoginDto.getUsername());
    }
}
