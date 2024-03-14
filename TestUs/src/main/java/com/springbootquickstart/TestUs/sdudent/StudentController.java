package com.springbootquickstart.TestUs.sdudent;

import java.util.ArrayList;
import java.util.List;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/student")
public class StudentController {

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

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Button {
        private String name;
        private String url;
    }


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
    

