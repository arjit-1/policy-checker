package com.kuliza.demo.service;

import com.kuliza.demo.model.userDetails;
import com.kuliza.demo.repository.UserRepository;
import com.kuliza.demo.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        userDetails userdetails = repo.findByUser_name(username);

        if (userdetails == null) throw new UsernameNotFoundException("User Not Found");

        return new CustomUserDetails(userdetails);
    }
}
