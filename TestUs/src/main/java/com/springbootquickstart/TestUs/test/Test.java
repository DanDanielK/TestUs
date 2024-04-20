package com.springbootquickstart.TestUs.test;

import java.util.List;
import com.springbootquickstart.TestUs.questions.Question;
import java.time.LocalDateTime;
import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Question.class)
    private List<Question> questions;

}
