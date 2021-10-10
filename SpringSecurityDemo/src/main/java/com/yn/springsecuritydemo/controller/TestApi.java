package com.yn.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/set-cookie/{cookie}")
    public String setCookie(@PathVariable("cookie") String cookie, HttpServletResponse response) {
        response.addCookie(new Cookie("cookieName", cookie));
        return "set cookie: " + cookie;
    }

    @PostMapping("/post")
    public String testPost(@RequestParam("name") String name) {
        return name;
    }


}
