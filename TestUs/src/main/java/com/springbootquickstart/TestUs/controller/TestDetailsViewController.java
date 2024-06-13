package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.model.Test;
import com.springbootquickstart.TestUs.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TestDetailsViewController {

    private final TestRepository testRepository;

    @Autowired
    public TestDetailsViewController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("/view-questions")
    public String viewQuestions(@RequestParam("testId") int testId, Model model) {
        // Retrieve the test from the repository using the testId
        Optional<Test> optionalTest = testRepository.findById(testId);

        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            // Add the test object to the model to pass it to the view
            model.addAttribute("test", test);
            // Return the name of the view template for displaying the questions
            return "view-questions";
        } else {
            // If test is not found, handle the error accordingly (redirect or show error
            // message)
            // For example, you can redirect the user back to the view tests page
            return "redirect:/view-tests";
        }
    }
}