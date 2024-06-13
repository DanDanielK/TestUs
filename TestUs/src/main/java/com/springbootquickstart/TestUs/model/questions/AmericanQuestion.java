package com.springbootquickstart.TestUs.model.questions;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "americanquestions")
@PrimaryKeyJoinColumn(name = "id")
public class AmericanQuestion extends Question {

    @Column
    private String option1;
    @Column
    private String option2;
    @Column
    private String option3;
    @Column
    private String option4;

    public AmericanQuestion(String questionText, String correctAnswer, String option1, String option2, String option3,
            String option4) {
        super(questionText, correctAnswer);
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    @Override
    public String getType() {
        return "American";
    }

    public List<String> getOptions(){
        
        try{
            
        return Arrays.asList(
            this.option1 ,
            this.option2 ,
            this.option3 ,
            this.option4 );
        }
        catch(Exception e){
            return  null;
        }

    }


    public String getCorrectAnswerAsString(){
        
        String number = this.getCorrectAnswer().substring("option".length());

        int correctAnswer = Integer.parseInt(number);

        return getOptions().get(correctAnswer - 1);
    }

}
