package com.carrental.controller;

import com.carrental.dto.SignupRequest;
import com.carrental.dto.UserDto;
import com.carrental.entity.User;
import com.carrental.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
        if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Customer with this email already exists", HttpStatus.NOT_ACCEPTABLE);

        UserDto createdUserDto = authService.createCustomer(signupRequest);
        if (createdUserDto==null)
            return new ResponseEntity<>("Customer not created, Come again later", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

}
