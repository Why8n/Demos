package com.yn.mybatisintegrationdemo.controller;

import com.yn.mybatisintegrationdemo.entity.User;
import com.yn.mybatisintegrationdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/home")
    public String index() {
        return "user home page";
    }


    @PostMapping
    public String addUser(@RequestBody User user) {
        this.userService.addUser(user);
        return "add user done!";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") long userId) {
        this.userService.deleteUser(userId);
        return "delete user done!";
    }

    @PutMapping
    public String udpateUser(@RequestBody User user) {
        this.userService.updateUser(user);
        return "update user done!";
    }

    @GetMapping
    public List<User> getAllUser() {
        return this.userService.getAllUser();
    }
}
