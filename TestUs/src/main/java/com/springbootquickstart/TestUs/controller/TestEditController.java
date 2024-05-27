package com.springbootquickstart.TestUs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Teacher;
import com.springbootquickstart.TestUs.questions.Question;
import com.springbootquickstart.TestUs.repository.CourseRepository;
import com.springbootquickstart.TestUs.service.CourseService;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import com.springbootquickstart.TestUs.service.TeacherService;
import com.springbootquickstart.TestUs.test.Test;
import com.springbootquickstart.TestUs.test.TestService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teacher")
public class TestEditController {

    @Autowired
    private TestService testService;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/edit-test")
    public String editTest(@RequestParam("testId") int testId, Model model) {
        System.out.println("Received testId: " + testId);
        Test test = testService.getTestById(testId);
        List<Question> questions = test.getQuestions();
        model.addAttribute("test", test);
        model.addAttribute("questions", questions);
        return "edit-test";
    }

    @PostMapping("/update-test")
    public String updateTest(@ModelAttribute Test test) {

        Course course = courseRepository.findCourseByTestId(test.getTestId());
        test.setCourse(course);
        // test.setQuestions(questions);
        testService.saveTest(test);
        return "redirect:/teacher/view-tests"; // redirect to the list of tests after updating
    }

    @DeleteMapping("/delete-question/{questionId}")

    @ResponseBody
    public String deleteQuestion(@PathVariable long questionId) {
        testService.deleteQuestionById(questionId);
        return "Question deleted";
    }

}
