package com.yn.springsecuritydemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginApi {

    @GetMapping("/login.html")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/success")
    @ResponseBody
    public String loginSuccessForward() {
        return "login success";
    }
}

