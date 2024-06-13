package com.springbootquickstart.TestUs.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.springbootquickstart.TestUs.model.answers.Answer;
import com.springbootquickstart.TestUs.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootquickstart.TestUs.model.questions.AmericanQuestion;
import com.springbootquickstart.TestUs.model.questions.Question;
import com.springbootquickstart.TestUs.model.questions.TrueFalseQuestion;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TestService testService;


    public Answer saveOrUpdateAnswer(int testId, int studentId, Map<Long, String> answers, boolean submitted) {
        /*
         * this method saves or updates an answer for a student for a test
         * 
         * @param: testId - the ID of the test
         * @param: studentId - the ID of the student
         * @param: answers - a map of question IDs to answers
         * @param: submitted - a boolean indicating whether the student has submitted the test
         * @return: the saved or updated answer
         * 
         */

        Answer existingAnswer = answerRepository.findByTestIdAndStudentId(testId, studentId);
        if (existingAnswer != null) {
            existingAnswer.getAnswers().putAll(answers);
            existingAnswer.setSubmitted(submitted);
            return answerRepository.save(existingAnswer);
        } else {
            Answer newAnswer = new Answer(testId, studentId, answers, submitted);
            return answerRepository.save(newAnswer);
        }
    }

    public Answer getAnswerByTestIdAndStudentId(int testId, int studentId) {
        /*
         * this method retrieves an answer by test ID and student ID
         * 
         * @param: testId - the ID of the test
         * @param: studentId - the ID of the student
         * @return: the answer
         * 
         */
        return answerRepository.findByTestIdAndStudentId(testId, studentId);
    }

    public boolean hasStudentSubmittedTest(int testId, int studentId) {
        /*
         * this method checks if a student has submitted a test
         * 
         * @param: testId - the ID of the test
         * @param: studentId - the ID of the student
         * @return: a boolean indicating whether the student has submitted the test
         * 
         */
        Answer answer = answerRepository.findByTestIdAndStudentId(testId, studentId);
        return answer != null && answer.isSubmitted();
    }

    public double getScore(int testId, int studentId) {
        /*
         * this method calculates the score of a student in a test
         * 
         * @param: testId - the ID of the test
         * @param: studentId - the ID of the student
         * @return: the score of the student in the test
         * 
         */
        Answer answer = answerRepository.findByTestIdAndStudentId(testId, studentId);
        if (answer == null) return -1.0;

        double score = 0.0;

        List<Question> testQuestions = testService.getTestById(testId).getQuestions();
        Map<Long, String> studentAnswers = answer.getAnswers();

        double questionPoints = 100.0 / testQuestions.size();

        for (Question question : testQuestions) {
            if (studentAnswers.containsKey(question.getId())) {

                if(question instanceof AmericanQuestion){
                    if(studentAnswers.get(question.getId()).equals(((AmericanQuestion)question).getCorrectAnswerAsString())){
                        score += questionPoints;
                    }
                }
                else if(question instanceof TrueFalseQuestion){
                    if(studentAnswers.get(question.getId()).equals(question.getCorrectAnswer())){
                        score += questionPoints;
                    }
                }
            }
        }

        return Double.parseDouble(new DecimalFormat("##.#").format(score));
        
    }



    
}
