package com.springbootquickstart.TestUs.service;

import com.springbootquickstart.TestUs.dto.UserRegisteredDto;
import com.springbootquickstart.TestUs.helper.UserFoundException;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Role;
import com.springbootquickstart.TestUs.model.Student;
import com.springbootquickstart.TestUs.model.Teacher;
import com.springbootquickstart.TestUs.repository.MyUserRepository;

import com.springbootquickstart.TestUs.repository.StudentRepository;
import com.springbootquickstart.TestUs.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    private MyUserRepository myUserRepo;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;



    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Optional<MyUser> findByUsername(String username) {
        return myUserRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserRepo.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
              return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();

        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String getRoles(MyUser user) {
        if (user.getRole() == null) {
            return  "STUDENT";
        }
        return user.getRole().toString();
    }


    public void save(UserRegisteredDto userRegisteredDTO) throws UserFoundException {
//        Role role =STUDENT;
//        if(userRegisteredDTO.getRole().equals("STUDENT"))
//            role = roleRepo.findByRole("STUDENT");
//        else if(userRegisteredDTO.getRole().equals("ADMIN"))
//            role = roleRepo.findByRole("ADMIN");
        if (myUserRepo.findByUsername(userRegisteredDTO.getUserName()).isPresent()) {
            throw new UserFoundException("User already exists");
            //  new RuntimeException("User already exists");
        }
        MyUser user = new MyUser();
        user.setUsername(userRegisteredDTO.getUserName());
        user.setFirstName(userRegisteredDTO.getFirstName());
        user.setLastName(userRegisteredDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
        user.setRole(Role.valueOf(userRegisteredDTO.getRole()));
        myUserRepo.save(user);

        if (userRegisteredDTO.getRole().equals("TEACHER")) {
            Teacher teacher = new Teacher();
            teacher.setMyUser(user);
            teacherRepository.save(teacher);
        }
        if (userRegisteredDTO.getRole().equals("STUDENT")) {
            Student student = new Student();
            student.setMyUser(user);
            studentRepository.save(student);
        }
    }
}
