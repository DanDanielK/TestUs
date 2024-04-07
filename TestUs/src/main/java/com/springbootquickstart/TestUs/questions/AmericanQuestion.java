package com.springbootquickstart.TestUs.questions;

import java.util.List;

public class AmericanQuestion extends Question {

    public AmericanQuestion(String questionText, List<Option> options, String correctAnswer) {
        super(questionText, correctAnswer, options);
    }

    @Override
    public String getType() {
        return "American";
    }

}
