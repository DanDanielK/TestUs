package com.springbootquickstart.TestUs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springbootquickstart.TestUs.questions.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}