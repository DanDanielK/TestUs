package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
