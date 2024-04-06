package com.springbootquickstart.TestUs.questions;

public class TrueFalseQuestion {
    public TrueFalseQuestion(String questionText, String correctAnswer) {
        super(questionText, correctAnswer);
    }

    @Override
    public String getType() {
        return "TrueFalse";
    }
}
