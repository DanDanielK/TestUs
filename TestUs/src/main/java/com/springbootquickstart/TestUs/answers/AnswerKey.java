package com.springbootquickstart.TestUs.answers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
//@Embeddable
public class AnswerKey implements Serializable {

    //@Column(name = "student_id")
    private int studentId;

    //@Column(name = "test_id")
    private int testId;

}
