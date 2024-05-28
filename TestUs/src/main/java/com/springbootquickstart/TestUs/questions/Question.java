package com.springbootquickstart.TestUs.questions;

import com.springbootquickstart.TestUs.test.Test;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Data
@Table(name = "questions")
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    private String correctAnswer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Test test;

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    // Abstract method to get the type of question
    public abstract String getType();
}