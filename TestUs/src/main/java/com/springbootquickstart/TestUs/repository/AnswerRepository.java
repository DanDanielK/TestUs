package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.answers.Answer;
import com.springbootquickstart.TestUs.model.answers.AnswerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, AnswerKey> {

    // List<Answer> findByStudent(Student student);

    // List<Answer> findByTest(Test test);

    // List<Answer> findByStudentId(Long studentId);

    // List<Answer> findByTestId(Long testId);

    // List<Answer> findBySubmitted(boolean submitted);

     // Custom query to find an answer by test ID and student ID
    @Query("SELECT a FROM Answer a WHERE a.testId = :testId AND a.studentId = :studentId")
    Answer findByTestIdAndStudentId(@Param("testId") int testId, @Param("studentId") int studentId);
}