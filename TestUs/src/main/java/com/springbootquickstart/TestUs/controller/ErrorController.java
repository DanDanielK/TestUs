package com.springbootquickstart.TestUs.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ErrorController {

    @RequestMapping("/error")
    public String handleError() {

        // Forward to the custom error page
        return "error";
    }


    @GetMapping("/{wrongUrl}")
    public String handleWrongUrl(@PathVariable String wrongUrl) {
        // Redirect to the error page
        return "redirect:/error";
    }

}