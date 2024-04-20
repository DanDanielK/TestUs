package com.springbootquickstart.TestUs.questions;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "americanquestions")
public class AmericanQuestion extends Question {

    public AmericanQuestion(String questionText, List<Option> options, String correctAnswer) {
        super(questionText, correctAnswer, options);
    }

    @Override
    public String getType() {
        return "American";
    }

}
