package com.springbootquickstart.TestUs.test;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    // You can add custom query methods here if needed
    List<Test> findByCourseId(Long courseId);

}