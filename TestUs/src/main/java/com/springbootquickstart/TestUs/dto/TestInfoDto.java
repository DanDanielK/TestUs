package com.springbootquickstart.TestUs.dto;

import java.time.LocalDateTime;
import java.time.Duration;

import com.springbootquickstart.TestUs.test.Test;

import lombok.Data;


@Data
public class TestInfoDto {

    private Test testClass;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;
    private boolean submitted;
    private double score;


    public TestInfoDto(Test test, double score) {
        this.testClass = test;
        this.startTime = test.getStartTime();
        this.endTime = test.getStartTime().plusMinutes(test.getDuration());
        this.duration = test.getDuration();
        this.score = score;
    }

    public boolean isTestActive(){
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }

    public boolean isTestUpcoming(){
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(startTime);
    }

    public boolean isTestCompleted(){
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endTime);
    }

    public Duration getRemainingTime() {
        LocalDateTime now = LocalDateTime.now();

        // If the current time is before startTime or after endTime, return a duration of zero
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            return Duration.ZERO;
        }

        // Calculate the duration between now and endTime
        return Duration.between(now, endTime);
    }

    public boolean isSubmitted(){
        return submitted;
    }  
    
}
