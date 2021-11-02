package com.yn.mybatisintegrationdemo.service.impl;

import com.yn.mybatisintegrationdemo.dao.IUserMapper;
import com.yn.mybatisintegrationdemo.entity.User;
import com.yn.mybatisintegrationdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    @Override
    public void addUser(User user) {
        this.userMapper.addUser(user);
    }

    @Override
    public void deleteUser(long id) {
        this.userMapper.deleteUserById(id);
    }

    @Override
    public void updateUser(User user) {
        this.userMapper.updateUserByName(user);
    }

    @Override
    public List<User> getAllUser() {
        return this.userMapper.selectAll();
    }
}
