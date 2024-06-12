package com.springbootquickstart.TestUs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbootquickstart.TestUs.dto.TestCreationDto;
import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.service.TestService;
import java.time.LocalDateTime;
import jakarta.servlet.http.HttpServletRequest;
import com.springbootquickstart.TestUs.questions.*;
import com.springbootquickstart.TestUs.service.CourseService;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import com.springbootquickstart.TestUs.service.TeacherService;

import org.springframework.ui.Model;

import java.util.*;

@Controller
public class TestCreationController {

    private TestService testService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private MyUserDetailService userService;

    @Autowired
    private TeacherService teacherService;

    public TestCreationController(TestService service) {
        this.testService = service;
    }

    @GetMapping("/teacher/create-test")
    public String showCreateTestForm(@ModelAttribute("test") TestCreationDto test, Model model) {
        MyUser user = userService.returnMyUser();
        int teacherID = teacherService.findById(user.getId()).getId();
        List<Course> courses = courseService.findCoursesByTeacherId(teacherID);
        model.addAttribute("courses", courses);
        return "create-test";
    }

    @PostMapping("/teacher/create-test")
    public String createTest(HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String title = request.getParameter("title");
        String startTimeStr = request.getParameter("startTime");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
        int duration = Integer.parseInt(request.getParameter("duration"));
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        List<Question> questionList = new ArrayList<>();
        int i = 1;
        while (request.getParameter("question" + i) != null) {
            String questionType = request.getParameter("questionType" + i);
            String questionText = request.getParameter("question" + i);
            String correctAnswer = request.getParameter("correctAnswer" + i);
            if (questionType.equals("american")) {
                String option1 = request.getParameter("option1" + i);
                String option2 = request.getParameter("option2" + i);
                String option3 = request.getParameter("option3" + i);
                String option4 = request.getParameter("option4" + i);
                AmericanQuestion americanQuestion = new AmericanQuestion(questionText, correctAnswer, option1, option2,
                        option3, option4);
                questionList.add(americanQuestion);

            } else if (questionType.equals("truefalse")) {
                // Create TrueFalseQuestion object
                TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion(questionText, correctAnswer);
                questionList.add(trueFalseQuestion);
            }
            i++;
        }
        TestCreationDto testDto = new TestCreationDto();
        testDto.setTitle(title);
        testDto.setStartTime(startTime);
        testDto.setDuration(duration);
        testDto.setQuestions(questionList);
        testDto.setCourseId(courseId);
        testService.createTest(testDto);
        String s = "Test created succesfully! number of questions saved: " + testDto.getQuestions().size();
        redirectAttributes.addFlashAttribute("successMessage", s);
        return "redirect:/teacher/create-test";
    }

    @PostMapping("/teacher/add-question")
    public String addQuestion(String questionType, RedirectAttributes redirectAttributes) {
        // Redirect to the appropriate page based on the selected question type
        if ("american".equals(questionType)) {
            return "redirect:/teacher/create-american-question";
        } else if ("truefalse".equals(questionType)) {
            return "redirect:/teacher/create-truefalse-question";
        } else {
            // Invalid question type, handle accordingly
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid question type selected.");
            return "redirect:/teacher/create-test";
        }
    }

    @PostMapping("/teacher/save-test")
    public String saveTest(@ModelAttribute TestCreationDto test,
            RedirectAttributes redirectAttributes) {
        testService.createTest(test);
        String s = "Test saved succesfully! number of questions saved: " + test.getQuestions().size();
        redirectAttributes.addFlashAttribute("successMessage", s);
        return "redirect:/teacher/create-test";
    }
}