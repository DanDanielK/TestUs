package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByMyUser(MyUser myUser);

    @Query("SELECT s FROM Student s JOIN Answer a ON s.id = a.studentId WHERE a.testId = :testId")
    List<Student> findStudentsByTestId(@Param("testId") Integer testId);

}
