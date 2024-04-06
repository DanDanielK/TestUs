package com.springbootquickstart.TestUs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.springbootquickstart.TestUs")
public class TestUsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestUsApplication.class, args);

	}

}
