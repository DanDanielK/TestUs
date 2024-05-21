package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.model.Course;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MyUserDetailService myUserDetailService;

    public Student findByMyUser(MyUser myUser){
        Optional<Student> student=studentRepository.findByMyUser(myUser);
        if(student.isPresent()){
            return student.get();
        }else{
            throw new UsernameNotFoundException("Student not found for this user"+myUser.getEmail()+" id"+myUser.getId());
        }
    }

    public Student findById(Long id) {
        Optional<MyUser> studentMyUser = myUserDetailService.findById(id);
        if (studentMyUser.isPresent()) {
            return findByMyUser(studentMyUser.get());
        }
        throw new UsernameNotFoundException("User Id not found" + id);
    }

    public Student findByEmail(String email) {
        Optional<MyUser> studentMyUser = myUserDetailService.findByEmail(email);
        if (studentMyUser.isPresent()) {
            return findByMyUser(studentMyUser.get());
        }
        throw new UsernameNotFoundException("User Email not found" + email);

    }

    public void save(Student student){
        studentRepository.save(student);
    }






}
