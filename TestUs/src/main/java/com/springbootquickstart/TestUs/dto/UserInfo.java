package com.springbootquickstart.TestUs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfo {
    private String email;
    private String fullName;
}