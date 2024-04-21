package com.springbootquickstart.TestUs.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.springbootquickstart.TestUs.questions.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCreationDto {
    private String title;
    private LocalDateTime startTime;
    private int duration;
    private List<Question> questions;
}
