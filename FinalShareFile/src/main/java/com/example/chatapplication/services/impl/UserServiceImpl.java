package com.example.chatapplication.services.impl;

import com.example.chatapplication.domain.User;
import com.example.chatapplication.repositories.UserRepository;
import com.example.chatapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void register(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateInfoAccount(User user) {
        userRepository.save(user);
    }
}
