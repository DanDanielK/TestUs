package com.springbootquickstart.TestUs.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbootquickstart.TestUs.dto.RegisterDto;
import com.springbootquickstart.TestUs.sdudent.StudentController.Button;
import com.springbootquickstart.TestUs.user.User;
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


    @GetMapping("/view-{role:(?:students|coordinators)}")
    public String viewUsers(@PathVariable("role") String role, Model model) {

        List<User> usersList = new ArrayList<>();


        //DODO: get users by role
        // Optional<User> users = Optional.empty();

        // if (role.equals("coordinators")) {
            
        //     //users = userRepository.findByRolesName("coordinator");
        // } else {
        //     //users = userRepository.findByRolesName("student");
        // }

        // //model.addAttribute("users", users);

        
        User user1 = new User((long) 209355234, "John", "Doe", "ssss@gggm.com", "password");

        
        usersList.add(user1); //remove
        
        model.addAttribute("usersList", usersList);

        return "viewUsers";
    }


    @GetMapping("/view-{role:(?:students|coordinators)}/delete")
    public String deleteUser(@PathVariable("role") String role, @RequestParam("id") Long id, RedirectAttributes r, Model model) {

        try {
            
            //handle href link
            model.addAttribute("role", role);

            //delete user
            userService.deleteUser(id); 
            
            //indicate success
            r.addFlashAttribute("successMessage", " user with ID " + id + " has been deleted.");

        } catch (Exception e) {
            r.addFlashAttribute("failMessage", e.getMessage());
        }

        //return to the same page
        return "redirect:/admin/view-" + role;
    }

    @GetMapping("/view-{role:(?:students|coordinators)}/edit")
    public String editeUser(@PathVariable("role") String role, @RequestParam("id") Long id, RedirectAttributes r, Model model) {

        try {

            //handle href link
            model.addAttribute("role", role);

            //get user by id
            User user = userService.getUser(id);
            
            //send user info to the edit page
            model.addAttribute("user", user);


        } catch (Exception e) {
            r.addFlashAttribute("failMessage", e.getMessage());
            //return the same page
            return "redirect:/admin/view-" + role;
        }

        //return the edit page
        return "redirect:/admin/view-" + role + "/edit";
    }


}
