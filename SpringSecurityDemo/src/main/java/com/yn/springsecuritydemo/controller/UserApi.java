package com.yn.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("user")
public class UserApi {

    private List<String> users = Stream.of("David","Roy","Linda").collect(Collectors.toList());

    @GetMapping
    public List<String> getUsers() {
        return this.users;
    }

    @GetMapping("/{userId}")
    public String getUser(@PathVariable("userId") Integer userId) {
        return this.users.get(userId);
    }

    @PostMapping
    public List<String> createUser(@RequestParam("name") String username) {
        System.out.println("username = " + username);
        this.users.add(username);
        return this.users;
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable("userId") Integer userId) {
        return this.users.get(userId) + " update successfully!";
    }

    @DeleteMapping("/{userId}")
    public String removeUser(@PathVariable("userId") Integer userId) {
        String oldUser = this.users.remove(userId.intValue());
        return String.format("delete %s successfully！", oldUser);
    }
}
