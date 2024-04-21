package com.springbootquickstart.TestUs.questions;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "americanquestions")
public class AmericanQuestion extends Question {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;

    public AmericanQuestion(String questionText, List<Option> options, String correctAnswer) {
        super(questionText, correctAnswer);
        this.options = options;
    }

    @Override
    public String getType() {
        return "American";
    }

}
