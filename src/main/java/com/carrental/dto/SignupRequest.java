package com.carrental.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String name;

    private String password;


    public SignupRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }


}
