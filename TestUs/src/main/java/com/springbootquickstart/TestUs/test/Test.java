package com.springbootquickstart.TestUs.test;

import java.util.List;
import com.springbootquickstart.TestUs.questions.Question;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testId;
    private String title;
    private LocalDateTime startTime;
    private int duration;
    private List<Question> questions;

}
