package com.springbootquickstart.TestUs.questions;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "truefalsequestions")
public class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String questionText, String correctAnswer) {
        super(questionText, correctAnswer, null);
    }

    @Override
    public String getType() {
        return "TrueFalse";
    }
}
