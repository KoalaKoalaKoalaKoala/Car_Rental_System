package com.carrental.dto;

import com.carrental.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private UserRole userRole;


    public UserDto() {
    }


    public UserDto(String name, String email, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

}
