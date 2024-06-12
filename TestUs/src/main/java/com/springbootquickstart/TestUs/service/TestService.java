package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.repository.TestRepository;
import com.springbootquickstart.TestUs.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springbootquickstart.TestUs.dto.TestCreationDto;
import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.questions.Question;
import com.springbootquickstart.TestUs.repository.CourseRepository;
import com.springbootquickstart.TestUs.repository.QuestionRepository;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private TestRepository testRepository;
    // dor: added courserepository
    @Autowired
    private CourseRepository courseRepoistory;

    @Autowired
    private QuestionRepository questionRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional
    public void createTest(TestCreationDto testDto) {
        Logger logger = Logger.getLogger(getClass().getName());
        Test test = convertToTest(testDto);
        Course course = courseRepoistory.findById(testDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        test.setCourse(course);
        List<Question> questionsToAdd = new ArrayList<>();
        for (Question question : testDto.getQuestions()) {
            logger.info(question.getQuestionText());
            question.setTest(test);
            questionsToAdd.add(question);
        }
        test.getQuestions().addAll(questionsToAdd);
        testRepository.save(test);
    }

    public void saveTest(Test test) {
        testRepository.save(test);
        List<Question> newQuestions = test.getQuestions();
        System.out.println("number of questions: " + newQuestions.size());
        for (Question q : newQuestions) {
            questionRepository.save(q);
        }
    }

    private Test convertToTest(TestCreationDto testDto) {
        // Convert TestCreationDto to Test entity
        // This could involve mapping fields from testDto to test entity
        // and handling any additional logic required
        Test test = new Test();
        test.setTitle(testDto.getTitle());
        test.setQuestions(testDto.getQuestions());
        test.setDuration(testDto.getDuration());
        test.setStartTime(testDto.getStartTime());
        // Set other fields as needed
        return test;
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // dor: added getTestsByTeachID
    public List<Test> getTestsByTeacherID(int teacherID) {
        List<Course> coursesOfTeacher = courseRepoistory.findCoursesByTeacherId(teacherID);
        List<Test> tests = new ArrayList<>();
        for (Course course : coursesOfTeacher) {
            List<Test> testsOfCourse = testRepository.findByCourseId(course.getId());
            tests.addAll(testsOfCourse);
        }
        return tests;

    }

    public void deleteQuestionById(long questionId) {
        questionRepository.deleteById(questionId);
    }

    public Test getTestById(int testId) {
        return testRepository.findById(testId).orElseThrow(() -> new RuntimeException("Test not found"));
    }
}