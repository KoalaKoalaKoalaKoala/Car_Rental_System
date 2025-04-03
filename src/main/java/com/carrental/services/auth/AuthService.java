package com.carrental.services.auth;

import com.carrental.dto.SignupRequest;
import com.carrental.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);

}
