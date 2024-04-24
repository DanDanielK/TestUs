package com.springbootquickstart.TestUs.dto;

import com.springbootquickstart.TestUs.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisteredDto {
    @NotNull
    private String userName;

    private  String firstName;

    private String lastName;
    @NotNull
    private String password;

    private String role;



    public UserRegisteredDto() {
        super();
    }

    public UserRegisteredDto(String role) {
        super();
        this.role = role;
    }


}
