package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Teacher;
import com.springbootquickstart.TestUs.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MyUserDetailService myUserDetailService;

    public Teacher findByMyUser(MyUser myUser){
        Optional<Teacher> teacher=teacherRepository.findByMyUser(myUser);
        if(teacher.isPresent()){
            return teacher.get();
        }else{
            throw new UsernameNotFoundException("Teacher not found for this user"+myUser.getEmail()+" id"+myUser.getId());
        }
    }

    public Teacher findByUserId(Long id) {
        Optional<MyUser> teacherMyUser = myUserDetailService.findById(id);
        if (teacherMyUser.isPresent()) {
            return findByMyUser(teacherMyUser.get());
        }
        throw new UsernameNotFoundException("User Id not found" + id);
    }

    public Teacher findByTeacherId(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Teacher findByEmail(String email) {
        Optional<MyUser> teacherMyUser = myUserDetailService.findByEmail(email);
        if (teacherMyUser.isPresent()) {
            return findByMyUser(teacherMyUser.get());
        }
        throw new UsernameNotFoundException("User Email not found" + email);
    }


    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
}
