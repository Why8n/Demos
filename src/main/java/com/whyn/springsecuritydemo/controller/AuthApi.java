package com.whyn.springsecuritydemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whyn.springsecuritydemo.entity.User;
import com.whyn.springsecuritydemo.mapper.IUserDao;
import com.whyn.springsecuritydemo.service.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.ReadPendingException;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Autowired
    private IAuthService authService;

    // 用户注册接口
    @PostMapping("/signup")
    public boolean signUp(@RequestBody User user) {
        return this.authService.signUp(user);
    }

    // 用户登录接口
    @PostMapping("/signin")
    public void signIn(@RequestBody User user, HttpServletResponse response) {
        String jwtToken = this.authService.signIn(user);
        Optional.ofNullable(jwtToken)
                .ifPresentOrElse(token -> {
                    this.success(response, token);
                }, () -> {
                    this.failed(response);
                });
    }

    private void success(HttpServletResponse response, String jwtToken) {
        response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            writer.print("login successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void failed(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try (PrintWriter writer = response.getWriter()) {
            writer.print("login failed! username or password incorrect");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
