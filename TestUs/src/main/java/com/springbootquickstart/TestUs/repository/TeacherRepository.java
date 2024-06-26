package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByMyUser(MyUser myUser);

    Optional<Teacher> findById(Long id);

}
