package com.example.chatapplication.controllers;

import com.example.chatapplication.domain.User;
import com.example.chatapplication.exception.BusinessException;
import com.example.chatapplication.services.UserService;
import com.example.chatapplication.ultities.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@Controller
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    /**
     * Get page login
     *
     * @return
     */
    @GetMapping(value = {"/register"})
    public String login() {
        return "register";
    }

    /**
     * Register user
     * @return
     */
    @PostMapping(value = {"/register"})
    public String register(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(Constant.NameAttribute.USERNAME);
        String password = request.getParameter(Constant.NameAttribute.PASSWORD);
        password = passwordEncoder.encode(password);

        //check user exist
        User userGetByUsername = userService.findByUsername(username);
        if (!Objects.isNull(userGetByUsername)){
            throw  new BusinessException("Cannot register user");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.register(user);
        return "redirect:/login";
    }
}