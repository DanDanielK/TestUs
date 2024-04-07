package com.springbootquickstart.TestUs.test;

import org.springframework.stereotype.Service;
import java.util.List;
import com.springbootquickstart.TestUs.dto.TestCreationDto;

public interface TestService {
    void createTest(TestCreationDto test);

    List<Test> getAllTests();
}