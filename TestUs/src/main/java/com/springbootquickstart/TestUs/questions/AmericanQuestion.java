package com.springbootquickstart.TestUs.questions;

import java.util.List;

public class AmericanQuestion extends Question {
    private List<String> options;

    public AmericanQuestion(String questionText, List<String> options, String correctAnswer) {
        super(questionText, correctAnswer);
        this.options = options;
    }

    @Override
    public String getType() {
        return "American";
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

}
