package com.youxiu326.youxiu326;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: lihui
 * @Date: 2021-03-22 17:44
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHashTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String keyPrefix = "redis:hash";

    @Test
    public void testHash(){

        redisTemplate.opsForHash().put(keyPrefix,"key1","value1");
        redisTemplate.opsForHash().put(keyPrefix,"key2","value2");
        redisTemplate.opsForHash().put(keyPrefix,"key3",null);

        Object value3 = redisTemplate.opsForHash().get(keyPrefix, "key3");
        Object value4 = redisTemplate.opsForHash().get(keyPrefix, "key4");


        System.out.println(redisTemplate.opsForHash().hasKey(keyPrefix, "key3"));
        System.out.println(redisTemplate.opsForHash().hasKey(keyPrefix, "key6"));

        System.out.println("===");

    }

}
