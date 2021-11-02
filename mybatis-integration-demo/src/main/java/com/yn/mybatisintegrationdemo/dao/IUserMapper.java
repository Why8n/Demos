package com.yn.mybatisintegrationdemo.dao;

import com.yn.mybatisintegrationdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface IUserMapper {
    // 增：添加用户
    void addUser(User user);

    // 删：删除用户
    void deleteUserById(long id);

    // 改：修改用户信息
    void updateUserByName(User user);

    // 查：查询所有用户信息
    List<User> selectAll();
}
