package com.yn.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {

    @GetMapping("/index")
    public String index() {
        return "welcome to Spring Security!";
    }

    @GetMapping("/basic-auth")
    public String basicAuth() {
        return "basic auth pass!";
    }




}
