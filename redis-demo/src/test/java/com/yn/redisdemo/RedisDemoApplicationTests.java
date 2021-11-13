package com.yn.redisdemo;

import com.yn.redisdemo.service.RedisService;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedis() {
        redisService.string().set("gender", "male 男");
        String username = (String) redisService.string().get("gender");
//        Assert.isTrue("Whyn".equals(username),"username not the same!");
        System.out.println(username);
    }

    @Test
    public void testListRemove() {
        String listKey = "listKey";
        this.redisTemplate.delete(listKey);
        this.redisTemplate.opsForList().leftPush(listKey, "value01");
        this.redisTemplate.opsForList().leftPush(listKey, "value02");
        this.redisTemplate.opsForList().leftPush(listKey, "value03");
        this.redisTemplate.opsForList().leftPush(listKey, "value03");
        this.redisTemplate.opsForList().leftPush(listKey, "value04");
        this.redisTemplate.opsForList().leftPush(listKey, "value05");
        this.redisTemplate.opsForList().leftPush(listKey, "value06");

        List<Object> range = this.redisTemplate.opsForList().range(listKey, 0, -1);
        range.forEach(System.out::println);

        // 删除全部
//        Long removeNums = this.redisTemplate.opsForList().remove(listKey, 0, "value03");
        // 从尾到头删除
//        Long removeNums = this.redisTemplate.opsForList().remove(listKey, -2, "value03");
        // 从头到尾删除
        Long removeNums = this.redisTemplate.opsForList().remove(listKey, 1, "value03");
        System.out.println("remove nums: " + removeNums);

        List<Object> range2 = this.redisTemplate.opsForList().range(listKey, 0, -1);
        range2.forEach(System.out::println);
    }


    @Test
    public void testLisTrim() {
        String key = "listKeyTrimTest";
        this.redisTemplate.delete(key);
        this.redisTemplate.opsForList().rightPush(key, "value01");
        this.redisTemplate.opsForList().rightPush(key, "value02");
        this.redisTemplate.opsForList().rightPush(key, "value03");
        this.redisTemplate.opsForList().rightPush(key, "value04");

        this.redisTemplate.opsForList().range(key, 0, -1)
                .forEach(System.out::println);

        this.redisTemplate.opsForList().trim(key, 1, 2);
        System.out.println("----------- trim --------------");

        this.redisTemplate.opsForList().range(key, 0, -1)
                .forEach(System.out::println);
    }

    @Test
    public void testStringSetTimeout() throws InterruptedException {
        String key = "key_string_timeout";
        this.redisService.string().set(key, "20seconds", 20, TimeUnit.SECONDS);
        Object actual = this.redisService.string().get(key);
        System.out.println(actual);
        assertThat(actual, equalTo("20seconds"));

        new CountDownLatch(1).await(21, TimeUnit.SECONDS);

        actual = this.redisService.string().get(key);
        System.out.println(actual);
        assertThat(actual, Is.is(IsNull.nullValue()));
    }


    @Test
    public void testRedisService() {
        String keyString = "keyString";
        String expectString = "set String value";
        this.redisService.keys().delete(keyString);
        this.redisService.string().set(keyString, expectString);
        String actualString = (String) this.redisService.string().get("keyString");

        String keyHash = "keyHash";
        String hashKey = "hashKey";
        String expectHash = "set Hash value";
        this.redisService.keys().delete(keyHash);
        this.redisService.hash().put(keyHash, hashKey, expectHash);
        String actualHash = (String) this.redisService.hash().get(keyHash, hashKey);

        String keyList = "keyList";
        String expectList = "set List value";
        this.redisService.keys().delete(keyList);
        this.redisService.list().push(keyList, expectList);
        String actualList = (String) this.redisService.list().get(keyList, 0);

        String keySet = "keySet";
        String expectSet = "set Set value";
        this.redisService.keys().delete(keySet);
        this.redisService.set().add(keySet, expectSet);
        String actualSet = (String) this.redisService.set().pop(keySet);

        String keyZSet = "keyZSet";
        String expectZSet = "set Sorted Set value";
        this.redisService.keys().delete(keyZSet);
        this.redisService.zset().add(keyZSet, expectZSet, 1.0);
        Set<Object> actualZSet = this.redisService.zset().getZSet(keyZSet);

        assertAll("test RedisService",
                () -> Assertions.assertEquals(expectString, actualString),
                () -> Assertions.assertEquals(expectHash, actualHash),
                () -> Assertions.assertEquals(expectList, actualList),
                () -> Assertions.assertEquals(expectSet, actualSet),
                () -> assertThat(actualZSet.stream().findFirst().get(), equalTo(expectZSet))
        );


    }
}
