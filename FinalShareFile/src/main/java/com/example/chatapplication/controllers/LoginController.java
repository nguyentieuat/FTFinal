package com.example.chatapplication.controllers;

import com.example.chatapplication.domain.User;
import com.example.chatapplication.services.JwtUserDetailsService;
import com.example.chatapplication.services.UserService;
import com.example.chatapplication.ultities.Constant;
import com.example.chatapplication.ultities.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService userService;

    /**
     * Get page login
     *
     * @return
     */
    @GetMapping(value = {"", "/", "/login"})
    public String login() {
        return "login";
    }

    /**
     * Login to application by account, password.
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter(Constant.NameAttribute.USERNAME);
        String password = request.getParameter(Constant.NameAttribute.PASSWORD);
        boolean authenticate = authenticate(username, password);

        //if login fail then redirect to page login
        if (!authenticate) {
            return "redirect:/login";
        }

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);

        Cookie cookie = new Cookie(Constant.COOKIE_NAME, token);
        cookie.setPath(Constant.SLACK);
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);

        User user = userService.findByUsername(username);
        user.setLastLogin(LocalDateTime.now());
        userService.updateInfoAccount(user);
        return "redirect:/listFile";
    }

    private boolean authenticate(String username, String password) throws Exception {
        boolean result = false;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            result = true;
        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
            log.error(ExceptionUtils.getStackTrace(e));
        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

}