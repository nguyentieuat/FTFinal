package com.example.chatapplication.services;

import com.example.chatapplication.domain.User;

public interface UserService {
    /**
     * Get user by username
     * @param username
     * @return
     */
    User findByUsername(String username);

    void register(User user);

    void updateInfoAccount(User user);
}
