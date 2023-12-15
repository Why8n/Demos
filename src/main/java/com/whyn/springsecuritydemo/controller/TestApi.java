package com.whyn.springsecuritydemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class TestApi {

    // 测试
    @GetMapping("/test")
    public String index() {
        return "hello world!";
    }

    // 获取当前用户信息
    @GetMapping("/user")
    public String whoami() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Object principal = auth.getPrincipal();
        String password = (String) auth.getCredentials();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        HttpServletRequest request = (HttpServletRequest) auth.getDetails();

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("name: %s\n", name));
        builder.append(String.format("principal: %s\n", principal));
        builder.append(String.format("password: %s\n", password));
        builder.append(String.format("authorities: %s\n", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")))
        );
        builder.append(String.format("getDetails: %s\n", request));
        return builder.toString();
    }
}
