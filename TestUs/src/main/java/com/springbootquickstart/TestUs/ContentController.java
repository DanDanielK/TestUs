package com.springbootquickstart.TestUs;

import com.springbootquickstart.TestUs.model.MyUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

  @GetMapping("/home")
  public String handleWelcome() {
    return "home";
  }

  @GetMapping("/admin/home")
  public String handleAdminHome() {
    return "home_admin";
  }

  @GetMapping("/user/home")
  public String handleUserHome() {
    return "home_user";
  }


//  @GetMapping("/register")
//  public String showSignUpForm(Model model) {
//    model.addAttribute("user", new MyUser());
//    return "signup_form";
//  }
}
