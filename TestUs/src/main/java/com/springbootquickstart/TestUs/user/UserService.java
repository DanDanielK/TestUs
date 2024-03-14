package com.springbootquickstart.TestUs.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootquickstart.TestUs.dto.RegisterDto;

import lombok.NoArgsConstructor;


@Service
@NoArgsConstructor
public class UserService  {
    
    @Autowired    
    private UserRepository userRepo;


    public void registerUser(RegisterDto registerDto) throws Exception {


        //check unique id
        if (userRepo.findById(registerDto.getId()).isPresent()) {
            throw new Exception("Id is already registered");
        }

        //check unique email
        if (userRepo.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new Exception("Email is already registered");
        }

        //check password
        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new Exception("Password and Confirm Password do not match");
        } 

        User user = new User();
        user.setId(registerDto.getId());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        //user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPassword(registerDto.getPassword()); //replace with passwordEncoder
        //user.setRole(registerDto.getRole()); // add role  and if its cordinator set isEnabled to false
        
        
        //save new user
        userRepo.save(user);
    }
   
}