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
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean validatePassword(String password,MyUser user){
        return passwordEncoder.matches(password,user.getPassword());
    }

    public Optional<MyUser> findByEmail(String email) {
        return myUserRepo.findByEmail(email);
    }

    public Optional <MyUser> findById(long id) {
        return myUserRepo.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserRepo.findByEmail(username);
        if (user.isPresent()) {
            MyUser userObj = user.get();
              return User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                      .accountLocked(userObj.isAccountLocked())
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
        if (myUserRepo.findByEmail(userRegisteredDTO.getEmail()).isPresent()) {
            throw new UserFoundException("User already exists");
            //  new RuntimeException("User already exists");
        }
        MyUser user = new MyUser();
        user.setId(userRegisteredDTO.getId());
        user.setEmail(userRegisteredDTO.getEmail());
       user.setFirstName(userRegisteredDTO.getFirstName());
        user.setLastName(userRegisteredDTO.getLastName());
        user.setPhone(userRegisteredDTO.getPhone());
        user.setAddress(userRegisteredDTO.getAddress());
        user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
        user.setRole(Role.valueOf(userRegisteredDTO.getRole()));
        if (userRegisteredDTO.getRole().equals("ADMIN")) {
            myUserRepo.save(user);
        }
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

    private MyUser returnMyUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Optional<MyUser> myUser = myUserRepo.findByEmail(user.getUsername());
        if (myUser.isPresent()) {
            return myUser.get();
        }else{
            throw new UsernameNotFoundException(user.getUsername());
        }
    }

    public MyUser getUser(Long id) throws Exception {

        Optional<MyUser> user =myUserRepo.findById(id);

        if (user.isEmpty() ) {
            throw new Exception("Not found user with id: " + id);
        }

        return user.get();
    }
}
