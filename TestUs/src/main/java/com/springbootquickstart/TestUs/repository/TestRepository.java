package com.springbootquickstart.TestUs.repository;

import java.util.List;

import com.springbootquickstart.TestUs.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    // You can add custom query methods here if needed
    List<Test> findByCourseId(Long courseId);

}