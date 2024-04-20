package com.springbootquickstart.TestUs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.springbootquickstart.TestUs", "com.springbootquickstart.TestUs.test",
		"com.springbootquickstart.TestUs.questions",
		"com.springbootquickstart.TestUs.user", "com.springbootquickstart.TestUs.controller" })
@EntityScan({ "com.springbootquickstart.TestUs.test", "com.springbootquickstart.TestUs.questions",
		"com.springbootquickstart.TestUs.user", "com.springbootquickstart.TestUs.controller" })
public class TestUsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestUsApplication.class, args);

	}

}
