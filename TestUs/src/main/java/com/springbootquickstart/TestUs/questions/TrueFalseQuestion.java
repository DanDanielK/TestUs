package com.springbootquickstart.TestUs.questions;

public class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(String questionText, String correctAnswer) {
        super(questionText, correctAnswer, null);
    }

    @Override
    public String getType() {
        return "TrueFalse";
    }
}
