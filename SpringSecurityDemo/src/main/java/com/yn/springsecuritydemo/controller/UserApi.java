package com.yn.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserApi {

    private List<String> users = Arrays.asList("David", "Roy", "Linda");

    @GetMapping
    public List<String> getUsers() {
        return this.users;
    }

    @PostMapping
    public List<String> createUser(@RequestParam("name") String username) {
        this.users.add(username);
        return this.users;
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable("userId") Integer userId) {
        return this.users.get(userId) + "update successfully!";
    }

    @DeleteMapping("/{userId}")
    public String removeUser(@PathVariable("userId") Integer userId) {
        String oldUser = this.users.get(userId);
        boolean ret = this.users.remove(userId);
        return String.format("delete %s %s", oldUser, ret ? "successfully" : "failure");
    }


}
