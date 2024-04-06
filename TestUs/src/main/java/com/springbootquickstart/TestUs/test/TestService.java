package com.springbootquickstart.TestUs.test;

import com.springbootquickstart.TestUs.dto.TestCreationDto;

public interface TestService {
    void createTest(TestCreationDto test);
}