package com.springbootquickstart.TestUs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootquickstart.TestUs.answers.AnswerService;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.model.Teacher;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import com.springbootquickstart.TestUs.service.TeacherService;
import com.springbootquickstart.TestUs.model.Test;
import com.springbootquickstart.TestUs.service.TestService;
import com.springbootquickstart.TestUs.service.*;

import java.util.ArrayList;
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

    @Autowired
    private AnswerService answerService;

    @Autowired
    private StudentService studentService;

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

    @PostMapping("view-students-results")
    public String viewStudentsResults(@RequestParam("testId") Integer testId, Model model) {
        List<Student> students_did_test = studentService.getStudentsDidTest(testId);
        List<Double> students_Scores = new ArrayList<>();
        for (Student st : students_did_test) {
            if (answerService.hasStudentSubmittedTest(testId, st.getId())) {
                students_Scores.add(answerService.getScore(testId, st.getId()));
            } else {
                students_Scores.add(-1.0);
            }
        }
        model.addAttribute("students", students_did_test);
        model.addAttribute("scores", students_Scores);
        return "view-students-results";
    }

}