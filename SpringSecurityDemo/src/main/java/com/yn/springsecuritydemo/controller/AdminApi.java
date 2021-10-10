package com.yn.springsecuritydemo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminApi {

    @GetMapping
    public String showUserDetails() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
