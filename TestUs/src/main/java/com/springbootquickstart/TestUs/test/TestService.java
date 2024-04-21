package com.springbootquickstart.TestUs.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springbootquickstart.TestUs.dto.TestCreationDto;
import com.springbootquickstart.TestUs.test.Test;
import com.springbootquickstart.TestUs.test.TestRepository;
import java.util.List;

@Service
public class TestService {

    private TestRepository testRepository;

    public TestService(TestRepository repository) {
        this.testRepository = repository;
    }

    @Transactional
    public void createTest(TestCreationDto testDto) {
        Test test = convertToTest(testDto);
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