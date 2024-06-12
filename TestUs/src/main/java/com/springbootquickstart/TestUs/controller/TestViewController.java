package com.springbootquickstart.TestUs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Teacher;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import com.springbootquickstart.TestUs.service.TeacherService;
import com.springbootquickstart.TestUs.model.Test;
import com.springbootquickstart.TestUs.service.TestService;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TestViewController {

    @Autowired
    private TestService testService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MyUserDetailService userDetailService;

    @GetMapping("/view-tests")
    public String viewTests(Model model) {
        MyUser currentLoggedUser = userDetailService.returnMyUser();
        Teacher currentLoggedTeacher = teacherService.findByMyUser(currentLoggedUser);
        int teacherID = currentLoggedTeacher.getId();
        // Fetch tests from the database
        List<Test> tests = testService.getTestsByTeacherID(teacherID);
        // Add tests to the model
        model.addAttribute("tests", tests);
        // Return the view template
        return "view-tests";
    }

}