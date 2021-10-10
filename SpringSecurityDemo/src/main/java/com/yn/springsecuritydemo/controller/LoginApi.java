package com.yn.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/failure.html")
    public String hahawaPage() {
        return "failure";
    }

    @RequestMapping("/doLogout")
    public String doLogout() {
        System.out.println("doLogout.....");
        return "failure";
    }

}

