package com.codewithmithun.ecommerce.services.jwt;

import com.codewithmithun.ecommerce.entity.User;
import com.codewithmithun.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser =  userRepository.findFirstByEmail(username);

        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("Username not found ",null);
        System.out.println("Username: "+optionalUser.get().getEmail());
        System.out.println("Password: "+optionalUser.get().getPassword());

        return new org.springframework.security.core.userdetails.User(
                optionalUser.get().getEmail(),
                optionalUser.get().getPassword(),
                List.of(new SimpleGrantedAuthority(optionalUser.get().getRole().name()))
        );
    }
}
