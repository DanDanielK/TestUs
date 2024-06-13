package com.springbootquickstart.TestUs.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.springbootquickstart.TestUs.model.questions.Question;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestCreationDto {
    private String title;
    private LocalDateTime startTime;
    private int duration;
    private Long courseId;
    private List<Question> questions;

    public TestCreationDto() {
        this.questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public Long getCourseId() {
        return this.courseId;
    }

    public String getTitle() {
        return this.title;
    }

    public int getDuration() {
        return this.duration;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }



    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    
}
