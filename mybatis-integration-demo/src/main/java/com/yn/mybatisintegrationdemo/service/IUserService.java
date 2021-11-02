package com.yn.mybatisintegrationdemo.service;

import com.yn.mybatisintegrationdemo.entity.User;

import java.util.List;

public interface IUserService {
    void addUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    List<User> getAllUser();
}
