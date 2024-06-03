package com.springbootquickstart.TestUs.answers;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootquickstart.TestUs.questions.Question;
import com.springbootquickstart.TestUs.test.TestService;

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
        return answerRepository.findByTestIdAndStudentId(testId, studentId);
    }

    public boolean hasStudentSubmittedTest(int testId, int studentId) {
        Answer answer = answerRepository.findByTestIdAndStudentId(testId, studentId);
        return answer != null && answer.isSubmitted();
    }

    public double getScore(int testId, int studentId) {
        Answer answer = answerRepository.findByTestIdAndStudentId(testId, studentId);
        if (answer == null) return -1.0;

        double score = 0.0;

        List<Question> testQuestions = testService.getTestById(testId).getQuestions();
        Map<Long, String> studentAnswers = answer.getAnswers();

        double questionPoints = 100.0 / testQuestions.size();

        for (Question question : testQuestions) {
            if (studentAnswers.containsKey(question.getId())) {
                if (studentAnswers.get(question.getId()).equals(question.getCorrectAnswer())) {
                    score += questionPoints;
                }
            }
        }

        return Double.parseDouble(new DecimalFormat("##.#").format(score));
        
    }



    
}
