package com.springbootquickstart.TestUs.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbootquickstart.TestUs.dto.RegisterDto;
import com.springbootquickstart.TestUs.sdudent.StudentController.Button;
import com.springbootquickstart.TestUs.user.UserService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    /* admin main menu page */
    @GetMapping("")
    public String adminMenu(Model model) {

        final String[] menuItemsText = {"Add New Coordinator", "View Coordinators","View Students" , "Logout"};
        final String[] menuItemsUrl = {"add-coordinator", "view-coordinators","view-students", "logout"};

        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < menuItemsText.length; i++) {
            buttons.add(new Button(menuItemsText[i],  "admin/" + menuItemsUrl[i]));
        }

        model.addAttribute("buttons", buttons);

        //title of the menu
        model.addAttribute("menuTitle", "Admin Menu");

        return "mainMenu";  
    }


    @GetMapping("/add-coordinator")
    public String addCoordinator() {
        return "addCoordinator";
    }

    
    @PostMapping(path = "/add-coordinator" , consumes = "application/x-www-form-urlencoded")
    public String addCoordinatorPost(RegisterDto registerDto,RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            //change the role to coordinator - ONLY ADMIN CAN ADD COORDINATORS!!!
            registerDto.setRole("coordinator");

            userService.registerUser(registerDto);

            //add a flash attribute to indicate success
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful!");
        
            //redirect to the same page to clear the form
            return "redirect:" + request.getRequestURI();
        
        } catch (Exception e) {
            return null; //DOTO handle error UI
        }

        
    
    }
    
}
