package com.springbootquickstart.TestUs.questions;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "truefalsequestions")
@NoArgsConstructor
public class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String questionText, String correctAnswer) {
        super(questionText, correctAnswer);
    }

    @Override
    public String getType() {
        return "TrueFalse";
    }


}
