package com.yn.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


    @GetMapping("/one")
    public String one(HttpServletRequest request) {
        request.getSession().setAttribute("hello", "world");
        return "set attribute into Session";
    }

    @GetMapping("/two")
    public String two(HttpSession session) {
        String value = (String) session.getAttribute("hello");
        return value;
    }


}
