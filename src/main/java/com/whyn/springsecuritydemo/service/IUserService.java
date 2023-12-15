package com.whyn.springsecuritydemo.service;

import com.whyn.springsecuritydemo.entity.User;

public interface IUserService {
    User findUser(String name);
}
