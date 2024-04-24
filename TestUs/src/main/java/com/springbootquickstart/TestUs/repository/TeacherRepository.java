package com.springbootquickstart.TestUs.repository;

import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {


   // Teacher findByTeacherByMyUser(MyUser myUser);

}
