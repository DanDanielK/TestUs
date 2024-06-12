package com.springbootquickstart.TestUs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.questions.AmericanQuestion;
import com.springbootquickstart.TestUs.questions.Question;
import com.springbootquickstart.TestUs.questions.TrueFalseQuestion;
import com.springbootquickstart.TestUs.repository.CourseRepository;
import com.springbootquickstart.TestUs.repository.QuestionRepository;
import com.springbootquickstart.TestUs.model.Test;
import com.springbootquickstart.TestUs.service.TestService;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teacher")
public class TestEditController {

    @Autowired
    private TestService testService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/edit-test")
    public String editTest(@RequestParam("testId") int testId, Model model) {
        Test test = testService.getTestById(testId);
        if (test.getQuestions() == null) {
            test.setQuestions(new ArrayList<>());
        }
        model.addAttribute("test", test);
        return "edit-test";
    }

    @PostMapping("/update-test")
    public String updateTest(HttpServletRequest request) {
        int testId = Integer.parseInt(request.getParameter("testId"));
        String title = request.getParameter("title");
        String startTimeStr = request.getParameter("startTime");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
        int duration = Integer.parseInt(request.getParameter("duration"));
        List<Question> questionList = new ArrayList<>();
        int i = 0;
        // Loop through American and TrueFalse questions
        while (request.getParameter("questions[" + i + "].questionText") != null) {
            Long questionId = null;
            String questionIdStr = request.getParameter("questions[" + i + "].id");
            if (questionIdStr != null && !questionIdStr.isEmpty()) {
                questionId = Long.parseLong(questionIdStr);
            } else {
                questionId = questionRepository.getHighestQuestionId() + 1;
            }
            String questionType = request.getParameter("questions[" + i + "].type");
            String questionText = request.getParameter("questions[" + i + "].questionText");
            String correctAnswer = request.getParameter("questions[" + i + "].correctAnswer");
            if (questionType.equals("American")) {
                String option1 = request.getParameter("questions[" + i + "].option1");
                String option2 = request.getParameter("questions[" + i + "].option2");
                String option3 = request.getParameter("questions[" + i + "].option3");
                String option4 = request.getParameter("questions[" + i + "].option4");

                AmericanQuestion americanQuestion = new AmericanQuestion(questionText, correctAnswer, option1, option2,
                        option3, option4);
                americanQuestion.setTest(testService.getTestById(testId));
                americanQuestion.setId(questionId);
                questionList.add(americanQuestion);
            } else if (questionType.equals("TrueFalse")) {
                TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion(questionText, correctAnswer);
                trueFalseQuestion.setTest(testService.getTestById(testId));
                trueFalseQuestion.setId(questionId);
                questionList.add(trueFalseQuestion);
            }

            i++;
        }
        Test test = testService.getTestById(testId); // Assuming you have a method to fetch the test by ID
        test.setTitle(title);
        test.setStartTime(startTime);
        test.setDuration(duration);
        test.setQuestions(questionList);
        Course course = courseRepository.findCourseByTestId(testId);
        test.setCourse(course);
        testService.saveTest(test);
        return "redirect:/teacher/view-tests"; // Redirect to the list of tests after updating
    }

    @DeleteMapping("/delete-question/{questionId}")
    @ResponseBody
    public String deleteQuestion(@PathVariable long questionId) {
        // long questionId = Long.parseLong(request.getParameter("questionId"));
        System.out.println("REACHED DELETETING" + questionId);
        testService.deleteQuestionById(questionId);
        return "Question deleted";
    }

}
