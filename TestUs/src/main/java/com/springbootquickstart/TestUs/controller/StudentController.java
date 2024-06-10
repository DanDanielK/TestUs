package com.springbootquickstart.TestUs.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.None;
import com.springbootquickstart.TestUs.dto.TestCreationDto;
import com.springbootquickstart.TestUs.dto.TestInfoDto;
import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.CourseStudent;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.questions.Question;
import com.springbootquickstart.TestUs.repository.MyUserRepository;
import com.springbootquickstart.TestUs.service.Button;
import com.springbootquickstart.TestUs.service.CourseService;
import com.springbootquickstart.TestUs.service.CourseStudentService;
import com.springbootquickstart.TestUs.service.StudentService;
import com.springbootquickstart.TestUs.test.Test;
import com.springbootquickstart.TestUs.test.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

                TestInfoDto testDto = new TestInfoDto(test);
                testDtoList.add(testDto);
            }

            System.out.println("Course: " + course.getId());
        }
        
        model.addAttribute("testsDtoList",testDtoList);

        return "student/studentTests";
    }


    /*
     *  ---------------------------------  TAKE TEST  ---------------------------------
     */
    
     @GetMapping("/take-test")
     public String takeTest(@RequestParam("testId") int testId ,Model model){ 

        //find the student that is logged in 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findByEmail(auth.getName());

        //validate the page request
        Test test  = null;
        try{
            //if not found
            test = testService.getTestById(testId);

            //if the student is not enrolled in the course

            //if the test is not active

            //if the student has already taken the test
            
        }
        finally{
            if (test == null)
                return "redirect:/student/review-all-tests";
        }
        

        TestInfoDto testDto = new TestInfoDto(test);
 
        // test remaining time in minutes
        Duration remainingTime = testDto.getRemainingTime();

        if (!testDto.isTestActive()) {
            // If the test is not active, redirect the student to the review tests page
            return "redirect:/student/review-all-tests";
        }
        
        model.addAttribute("remainingTime", remainingTime.toMinutes());

        //test.getQuestions()
        List<Question> questions = new ArrayList<>(test.getQuestions());
        Question question = questions.get(0);
        //question.

         // Sample questions
        //  questions.add(new Question(1, "What is the capital of the USA?", List.of("Washington, D.C.", "New York", "Los Angeles", "Chicago", "Miami")));
        //  questions.add(new Question(2, "Who was the first President of the USA?", List.of("George Washington", "Abraham Lincoln", "Thomas Jefferson")));

        //  questions.add(new Question(3, "What is the capital of the USA?", List.of("Washington, D.C.", "New York", "Los Angeles")));
        //  questions.add(new Question(4, "Who was the first President of the USA?", List.of("George Washington", "Abraham Lincoln", "Thomas Jefferson")));

        //  questions.add(new Question(5, "What is the capital of the USA?", List.of("Washington, D.C.", "New York", "Los Angeles")));
        //  questions.add(new Question(6, "Who was the first President of the USA?", List.of("George Washington", "Abraham Lincoln", "Thomas Jefferson")));
         
         model.addAttribute("questions", questions);
        
         return "student/takeTest";
 
     }
 
     @PostMapping("/submit")
     public String submitAnswers() {
         // Handle the form submission
         return "result";
     }



    //  public class Question {
    //     private int id;
    //     private String text;
    //     private List<String> options;
    
    //     public Question(int id, String text, List<String> options) {
    //         this.id = id;
    //         this.text = text;
    //         this.options = options;
    //     }
    
    //     public int getId() {
    //         return id;
    //     }
    
    //     public void setId(int id) {
    //         this.id = id;
    //     }
    
    //     public String getText() {
    //         return text;
    //     }
    
    //     public void setText(String text) {
    //         this.text = text;
    //     }
    
    //     public List<String> getOptions() {
    //         return options;
    //     }
    
    //     public void setOptions(List<String> options) {
    //         this.options = options;
    //     }
    // }






    
    // public String viewQuestions(@RequestParam("testId") int testId, Model model) {
    //     // Retrieve the test from the repository using the testId
    //     Optional<Test> optionalTest = testRepository.findById(testId);

    //     if (optionalTest.isPresent()) {
    //         Test test = optionalTest.get();
    //         // Add the test object to the model to pass it to the view
    //         model.addAttribute("test", test);
    //         // Return the name of the view template for displaying the questions
    //         return "view-questions";
    //     } else {
    //         // If test is not found, handle the error accordingly (redirect or show error
    //         // message)
    //         // For example, you can redirect the user back to the view tests page
    //         return "redirect:/view-tests";
    //     }
    // }


}