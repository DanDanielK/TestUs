package com.springbootquickstart.TestUs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbootquickstart.TestUs.dto.TestCreationDto;
import com.springbootquickstart.TestUs.test.TestService;

@Controller
public class TestCreationController {

    private final TestService testService;

    public TestCreationController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/create-test")
    public String showCreateTestForm(@ModelAttribute("test") TestCreationDto test) {
        return "create-test";
    }

    @PostMapping("/create-test")
    public String createTest(@ModelAttribute("test") TestCreationDto test,
            RedirectAttributes redirectAttributes) {
        testService.createTest(test);
        redirectAttributes.addFlashAttribute("successMessage", "Test created successfully!");
        return "redirect:/create-test";
    }
}