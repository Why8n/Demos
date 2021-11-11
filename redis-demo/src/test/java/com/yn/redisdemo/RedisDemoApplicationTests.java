package com.yn.redisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

@SpringBootTest
class RedisDemoApplicationTests {

    // 注入 RedisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("username", "Whyn");
        String username = (String) redisTemplate.opsForValue().get("username");
        Assert.isTrue("Whyn".equals(username),"username not the same!");
        System.out.println(username);
    }
}
