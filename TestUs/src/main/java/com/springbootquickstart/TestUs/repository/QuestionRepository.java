package com.springbootquickstart.TestUs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springbootquickstart.TestUs.model.questions.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT MAX(id) FROM Question q")
    long getHighestQuestionId();
}