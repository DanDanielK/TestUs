package com.springbootquickstart.TestUs.answers;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AnswerKey.class)
public class Answer {

    @Id
    private int testId;

    @Id
    private int studentId;

    @ElementCollection
    @CollectionTable(name = "answer_entries", joinColumns = {
            @JoinColumn(name = "test_id", referencedColumnName = "testId"),
            @JoinColumn(name = "student_id", referencedColumnName = "studentId")
    })
    @MapKeyColumn(name = "question_id")
    @Column(name = "answer")
    private Map<Long, String> answers;

    private boolean submitted;

}
