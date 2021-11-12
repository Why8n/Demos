package com.yn.redisdemo.service;


import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Nested
    class StringOpsTest {

        private static final String KEY = "keyString";
        private static final String VALUE = "stringValue";

        @BeforeEach
        public void installString() {
            redisTemplate.opsForValue().set(KEY, VALUE);
        }

        @AfterEach
        public void tearDownString() {
            redisTemplate.delete(KEY);
        }

        @Test
        public void testSet() {
            String key = "stringKey_testSet";
            String expectValue = "test String set";
            redisService.string().set(key, expectValue);
            String value = (String) redisTemplate.opsForValue().get(key);
            assertThat(value, equalTo(expectValue));

            redisTemplate.delete(key);
        }

        @Test
        public void testGet() {
            String value = (String) redisService.string().get(KEY);
            assertThat(value, equalTo(VALUE));
        }

    }

    @Nested
    class HashOpsTest {

        private static final String KEY = "keyHash";
        private static final String HASH_KEY = "keyEntry";
        private static final String HASH_VALUE = "Entry Value";

        @BeforeEach
        public void installHash() {
            redisTemplate.opsForHash().put(KEY, HASH_KEY, HASH_VALUE);
        }

        @AfterEach
        public void tearDownHash() {
            redisTemplate.delete(KEY);
        }

        @Test
        public void testPut() {
            String key = "hashKey_testPut";
            String hashKey = "hashKey_keyEntry";
            String hashValue = "hashValue_valueEntry";
            redisService.hash().put(key, hashKey, hashValue);

            String actual = (String) redisTemplate.opsForHash().get(key, hashKey);
            assertThat(actual, equalTo(hashValue));

            redisTemplate.delete(key);
        }

        @Test
        public void testGet() {
            String actual = (String) redisService.hash().get(KEY, HASH_KEY);
            assertThat(actual, equalTo(HASH_VALUE));
        }
    }

    @Nested
    class ListOpsTest {
        private static final String KEY = "keyList";

        @BeforeEach
        public void initList() {
            redisTemplate.opsForList().rightPush(KEY, "value01");
            redisTemplate.opsForList().rightPush(KEY, "value02");
            redisTemplate.opsForList().rightPush(KEY, "value03");
        }

        @AfterEach
        public void tearDownList() {
            redisTemplate.delete(KEY);
        }

        @Test
        public void testListPush() {
            String listKey = "listKey";
            redisTemplate.delete(listKey);

            String value01 = "right push 01";
            String value02 = "left push 02";
            redisService.list().push(listKey, value01);
            redisService.list().leftPush(listKey, value02);

            redisTemplate.opsForList().range(listKey, 0, -1)
                    .forEach(System.out::println);

            Assertions.assertAll("比对列表条目",
                    () -> Assertions.assertEquals(value01, redisTemplate.opsForList().index(listKey, 1)),
                    () -> Assertions.assertEquals(value02, redisTemplate.opsForList().index(listKey, 0))
            );
        }

        @Test
        public void testListGet() {
            Object value = redisService.list().get(KEY, 1);
            assertThat(value, equalTo("value02"));
        }
    }

    @Nested
    class SetOpsTest {
        private static final String KEY = "keySet";

        @Test
        public void testPush() {
            redisService.keys().delete(KEY);
            redisService.set().add(KEY, "setValue01");
            redisService.set().add(KEY, "setValue02");
            redisService.set().add(KEY, "setValue01");

            redisService.set().getSet(KEY)
                    .forEach(System.out::println);

            assertThat(redisService.set().size(KEY), Is.is(2L));
        }
    }


}