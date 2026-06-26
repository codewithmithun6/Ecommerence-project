package com.codewithmithun.ecommerce.services;

import com.codewithmithun.ecommerce.dto.SignupRequest;
import com.codewithmithun.ecommerce.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
    Boolean hasUserWithEmail(String email);
    void createAdminAccount();
}
