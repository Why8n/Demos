package com.whyn.springsecuritydemo.mapper;

import com.whyn.springsecuritydemo.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IUserDaoTest {

    @Autowired
    private IUserDao userDao;

    @Test
    void testInsert() {
        User user = this.userDao.selectOneByName(1L);
        System.out.println(user);
    }

    @Test
    void testSelectOne() {
        User user = new User(3L, "yn", "yn_password", "normal", "read,create");
        boolean ret = this.userDao.insert(user) > 0;
        Assertions.assertThat(ret).isTrue();

        System.out.println(this.userDao.selectOneByName(3L));

    }
}