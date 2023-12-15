package com.whyn.springsecuritydemo.service.impl;

import com.whyn.springsecuritydemo.entity.User;
import com.whyn.springsecuritydemo.mapper.IUserDao;
import com.whyn.springsecuritydemo.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserDao userDao;
    @Override
    public User findUser(String name) {
        return this.userDao.selectOneByName(name);
    }
}
