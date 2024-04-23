package com.springbootquickstart.TestUs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RegisterDto {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
    private String confirmPassword;
    
}
