package com.kuliza.demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

//  INSTANCE OF THE USERDETAILS ENTITY CLASS......................
    private userDetails userdetails;

    //CONSTRUTOR OF THE CUSTOMUSERDETAILS CLASS.......................
    public CustomUserDetails(userDetails userdetails) {
        this.userdetails = userdetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userdetails.getUser_password();
    }

    @Override
    public String getUsername() {
        return userdetails.getUser_name();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public userDetails giveUserDetail(){return userdetails;}
}
