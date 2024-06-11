package com.springbootquickstart.TestUs.controller;

import com.springbootquickstart.TestUs.answers.Answer;
import com.springbootquickstart.TestUs.answers.AnswerService;
import com.springbootquickstart.TestUs.dto.TestInfoDto;
import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.questions.AmericanQuestion;
import com.springbootquickstart.TestUs.questions.Question;
import com.springbootquickstart.TestUs.questions.TrueFalseQuestion;

import com.springbootquickstart.TestUs.service.Button;
import com.springbootquickstart.TestUs.service.CourseService;
import com.springbootquickstart.TestUs.service.CourseStudentService;
import com.springbootquickstart.TestUs.service.StudentService;
import com.springbootquickstart.TestUs.test.Test;
import com.springbootquickstart.TestUs.test.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;


@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseStudentService courseStudentService;
    @Autowired
    private TestService testService;
    @Autowired
    private AnswerService answerService;

    /*
     *  ------------------------------------  MENU  -----------------------------------
     */

    @GetMapping("")
    public String studentMenu(Model model) {
        /* student main menu page */

        //student menu page options
        final String[] menuItemsText = {"view courses", "Review All Tests", "Logout"};
        final String[] menuItemsUrl = {"view-courses", "review-all-tests", "logout"};

        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < menuItemsText.length; i++) {
            buttons.add(new Button(menuItemsText[i],  "student/" + menuItemsUrl[i]));
        }

        model.addAttribute("buttons", buttons);

        //title of the menu
        model.addAttribute("menuTitle", "Student Menu");

        return "mainMenu";
    }


    /*
     *  --------------------------------  VIEW COURSES ------------------------------
     */

    @GetMapping("/view-courses")
    public String viewCourses(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student= studentService.findByEmail(auth.getName());
        List<Course> courseList=new ArrayList<>(courseStudentService.getCoursesByStudent(student));
    List<Course>coursesNotEnrolled=courseStudentService.findCoursesNotEnrolledByStudentId(student);
        model.addAttribute("courseStudentList",courseStudentService.findByStudent(student));
        model.addAttribute("coursesNotEnrolled",coursesNotEnrolled);
        return "student/courseView";
    }

    @RequestMapping(value="/addCourse", method= RequestMethod.POST)
    public String addCourse(@RequestParam("courseId") int courseId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());
        Course course = courseService.findById((long)courseId);
        courseStudentService.addStudentToCourse(course,student);
        return "redirect:/student/view-courses";
    }


    /*
     *  --------------------------------  VIEW TESTS ------------------------------
     */

    @GetMapping("/review-all-tests")
    public String reviewPastTests(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());
        
        List<Course> studentCourses = courseStudentService.getCoursesByStudent(student);

        List<TestInfoDto> testDtoList = new ArrayList<>();

        for( Course course : studentCourses){

            for(Test test : course.getTests()){

                TestInfoDto testDto = new TestInfoDto(test, answerService.getScore(test.getTestId(), student.getId())); // if score -1 the student has not taken the test

                // check if the student has already taken the test or if the test finished the student has not taken the test
                if (answerService.hasStudentSubmittedTest(test.getTestId(), student.getId())){
                    testDto.setSubmitted(true);
                }

                testDtoList.add(testDto);
            }

            System.out.println("Course: " + course.getId());
        }
        
        model.addAttribute("testsDtoList",testDtoList);

        return "student/studentTests";
    }

    @GetMapping("/view-test")
    public String reviewPastResults(@RequestParam("testId") int testId, Model model){

        try{
        Test test = testService.getTestById(testId);

        // split the questions
        List<AmericanQuestion> americanQuestions = new ArrayList<>();
        List<Question> trueFalseQuestions = new ArrayList<>();

        for (Question q : test.getQuestions()) {

            if (q instanceof AmericanQuestion) 
                americanQuestions.add((AmericanQuestion) q);
            
            else if(q instanceof TrueFalseQuestion)
                trueFalseQuestions.addLast((TrueFalseQuestion)q);

        }
            
        model.addAttribute("americanQ", americanQuestions);
        model.addAttribute("trueFalseQ", trueFalseQuestions);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());

        //student answers
        Answer answers = answerService.getAnswerByTestIdAndStudentId(testId, student.getId());

        if (answers == null) return "redirect:/student/review-all-tests";
        
        Map<Long, String> studentAnswers = answers.getAnswers();
        
        double score = answerService.getScore(testId, student.getId());

        model.addAttribute("aMap",studentAnswers);
        model.addAttribute("score",score);

        return "student/viewTestResults";
        }
        catch(Exception e){
            System.out.println("------------Error: " + e.getMessage());
            return "redirect:/student/review-all-tests";
        }
    }


    /*
     *  ---------------------------------  TAKE TEST  ---------------------------------
     */
    
    @GetMapping("/take-test")
    public String takeTest(@RequestParam("testId") int testId ,Model model){ 
        /**
         * take the test
         * 
         * @param testId: the id of the test
         * @param model: the model to pass the data to the view
         * @return: the view to take the test
         */

        //find the student that is logged in 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());

        Test test  = null;
        TestInfoDto testDto = null; 

        // validate the page request
        try{
            test = testService.getTestById(testId); // if not found

            if (test == null) throw new Exception("test is not active");
             
            testDto = new TestInfoDto(test, -1); // if the test is not active

            if (!testDto.isTestActive()) throw new Exception("test not found");
             
            //if the student has already taken the test
            if (answerService.hasStudentSubmittedTest(testId, student.getId())) throw new Exception("student has already taken the test");
            
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return "redirect:/student/review-all-tests";
        }
        
        // test remaining time in minutes
        Duration remainingTime = testDto.getRemainingTime();
        model.addAttribute("remainingTime", remainingTime.toMinutes());

        // split the questions
        List<AmericanQuestion> americanQuestions = new ArrayList<>();
        List<Question> trueFalseQuestions = new ArrayList<>();

        for (Question q : test.getQuestions()) {

            if (q instanceof AmericanQuestion) 
                americanQuestions.add((AmericanQuestion) q);
            
            else if(q instanceof TrueFalseQuestion)
                trueFalseQuestions.addLast((TrueFalseQuestion)q);
        }
 
        model.addAttribute("americanQ", americanQuestions);
        model.addAttribute("trueFalseQ", trueFalseQuestions);
        model.addAttribute("testId", test.getTestId());

        //if its first time save empty answers to know the student has started the test
        if (!answerService.hasStudentSubmittedTest(testId, student.getId())) {
            Map<Long, String> answers = new HashMap<Long, String>();
            for (Question q : test.getQuestions()) {
                answers.put(q.getId(), "");
            }
            
            answerService.saveOrUpdateAnswer(testId, student.getId(), answers, false);
        }
        
         return "student/takeTest";
        }
 
    @PostMapping("/submit")
    public String submitAnswers(@RequestParam Map<String, String> allParams){
        /**
         * handle the form (test) saving witout submission.
         * 
         * @param allParams: map of the answers
         * @return: redirect to the review-all-tests page
         * 
         * Note: testId saved in the HTML page as hidden input
         */

        return saveAnswersHelper(allParams, true);
    }

    @PostMapping("/save")
    public String saveAnswers(@RequestParam Map<String, String> allParams) {
        /**
         * handle the form (test) saving witout submission.
         * 
         * @param allParams: map of the answers
         * @return: redirect to the review-all-tests page
         * 
         * Note: testId saved in the HTML page as hidden input
         */
        return saveAnswersHelper(allParams, false);
    }


    private String saveAnswersHelper(@RequestParam Map<String, String> allParams, Boolean submitted) {
        /**
         * save the answers to the database
         * 
         * @param allParams: map of the answers
         * @param submitted: boolean, true if the test is submitted, false if 
         *                   the test is saved.
         * @return: redirect to the review-all-tests page
         */

        int testId = Integer.parseInt(allParams.get("testId"));
        Test test  = testService.getTestById(testId);

        if(test == null) return "redirect:/student/review-all-tests";

        // remove the testId from the map of the answers
        allParams.remove("testId");

        //find the student that is logged in 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());
        
        Map<Long, String> answers = new HashMap<Long, String>();

        // contain question ids as keys and answer as values
        for (Map.Entry<String, String> entry : allParams.entrySet()) 
            answers.put(Long.parseLong(entry.getKey()), entry.getValue());

        // save / update the answers to the database, submitted = true.
        answerService.saveOrUpdateAnswer(testId, student.getId(), answers, submitted);

        return "redirect:/student/review-all-tests";
        
    }
    
}