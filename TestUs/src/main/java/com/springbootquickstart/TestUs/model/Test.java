package com.springbootquickstart.TestUs.model;

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
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<Question> questions;
    // dor: added course_id to test
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
