package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.dto.UserLoginDto;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class AccountController {
    @Autowired
    private MyUserDetailService userDetailService;

    @ModelAttribute("user")
    public UserLoginDto userLoginDto() {
        return new UserLoginDto();
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "home";
    }

    @PostMapping
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void loginUser(@ModelAttribute("user") UserLoginDto userLoginDto) {
        userDetailService.loadUserByUsername(userLoginDto.getUsername());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        // You can redirect wherever you want, but generally it's a good practice to
        return "redirect:/";
    }



}
