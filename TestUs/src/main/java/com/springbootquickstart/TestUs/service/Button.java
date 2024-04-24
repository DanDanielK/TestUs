package com.springbootquickstart.TestUs.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
public class Button {
    private String name;
    private String url;


    @GetMapping("/take-test")
    public String takeTest() {
        return "mainMenu"; // returns the name of the HTML template for the take test page
    }

    @GetMapping("/view-results")
    public String viewResults() {
        return "view_results"; // returns the name of the HTML template for the view results page
    }

    @GetMapping("/review-tests")
    public String reviewTests() {
        return "review_tests"; // returns the name of the HTML template for the review tests page
    }


    @GetMapping("/logout")
    public String exit() {
        return "log"; // returns the name of the HTML template for the exit page
    }
}