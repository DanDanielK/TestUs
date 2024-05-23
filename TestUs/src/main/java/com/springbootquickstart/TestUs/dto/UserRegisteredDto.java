package com.springbootquickstart.TestUs.dto;

import com.springbootquickstart.TestUs.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserRegisteredDto {
    @Positive
    private Long id;
    @NotBlank
    private String email;

    private  String firstName;

    private String lastName;
    @NotBlank
    private String password;

    private String role;

    private String phone;

    private String address;





    public UserRegisteredDto() {
        super();
    }

    public UserRegisteredDto(String role) {
        super();
        this.role = role;
    }


}
