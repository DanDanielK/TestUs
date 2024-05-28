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
import com.springbootquickstart.TestUs.questions.AmericanQuestion;
import com.springbootquickstart.TestUs.questions.Question;
import com.springbootquickstart.TestUs.questions.TrueFalseQuestion;
import com.springbootquickstart.TestUs.repository.CourseRepository;
import com.springbootquickstart.TestUs.service.CourseService;
import com.springbootquickstart.TestUs.service.MyUserDetailService;
import com.springbootquickstart.TestUs.service.TeacherService;
import com.springbootquickstart.TestUs.test.Test;
import com.springbootquickstart.TestUs.test.TestService;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        // List<Question> questions = test.getQuestions();
        model.addAttribute("test", test);
        // model.addAttribute("questions", questions);
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
        while (request.getParameter("questionText" + i) != null) {
            String questionType = request.getParameter("questionType" + i);
            String questionText = request.getParameter("questionText" + i);
            String correctAnswer = request.getParameter("correctAnswer" + i);
            if (questionType.equals("American")) {
                String option1 = request.getParameter("option1" + i);
                String option2 = request.getParameter("option2" + i);
                String option3 = request.getParameter("option3" + i);
                String option4 = request.getParameter("option4" + i);
                AmericanQuestion americanQuestion = new AmericanQuestion(questionText, correctAnswer, option1, option2,
                        option3, option4);
                questionList.add(americanQuestion);
            } else if (questionType.equals("TrueFalse")) {
                TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion(questionText, correctAnswer);
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
        testService.deleteQuestionById(questionId);
        return "Question deleted";
    }

}
