package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.service.Button;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    /* teacher main menu page */
    @GetMapping("")
    public String teacherMenu(Model model) {

        // student menu page options
        final String[] menuItemsText = { "view tests", "create new test","Logout"};
        final String[] menuItemsUrl = {  "view-tests", "create-test", "logout" };

        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < menuItemsText.length; i++) {
            if (menuItemsUrl[i].equals("logout")) {
                buttons.add(new Button(menuItemsText[i], "/logout"));
            } else
            buttons.add(new Button(menuItemsText[i], "teacher/" + menuItemsUrl[i]));
        }

        model.addAttribute("buttons", buttons);

        // title of the menu
        model.addAttribute("menuTitle", "Teacher Menu");

        return "mainMenu";
    }
}
