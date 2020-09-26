package com.example.chatapplication.services;

import com.example.chatapplication.domain.User;
import com.example.chatapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = accountRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantList);
    }
}