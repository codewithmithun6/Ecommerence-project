package com.codewithmithun.ecommerce.services.impl;

import com.codewithmithun.ecommerce.dto.SignupRequest;
import com.codewithmithun.ecommerce.dto.UserDto;
import com.codewithmithun.ecommerce.entity.User;
import com.codewithmithun.ecommerce.enums.UserRole;
import com.codewithmithun.ecommerce.repository.UserRepository;
import com.codewithmithun.ecommerce.services.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthServiceImpl implements AuthService {


    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(SignupRequest signupRequest){

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);

//        UserDto userDto = new UserDto();
//        userDto.setId(createUser.getId());

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        return userDto;

    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    @Override
    public void createAdminAccount() {
        User adminAccount =  userRepository.findByRole(UserRole.ADMIN);

        if(null==adminAccount){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);

        }
    }
}
