package com.springbootquickstart.TestUs.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springbootquickstart.TestUs.dto.TestCreationDto;
import com.springbootquickstart.TestUs.questions.Question;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private TestRepository testRepository;

    public TestService(TestRepository repository) {
        this.testRepository = repository;
    }

    @Transactional
    public void createTest(TestCreationDto testDto) {
        Logger logger = Logger.getLogger(getClass().getName());
        Test test = convertToTest(testDto);
        List<Question> questionsToAdd = new ArrayList<>();
        for (Question question : testDto.getQuestions()) {
            logger.info(question.getQuestionText());
            question.setTest(test);
            questionsToAdd.add(question);
        }
        test.getQuestions().addAll(questionsToAdd);
        testRepository.save(test);
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
}