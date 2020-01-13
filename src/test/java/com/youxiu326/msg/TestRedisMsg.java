package com.youxiu326.msg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisMsg {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testConvertAndSend() {

        stringRedisTemplate.convertAndSend("chat", "牛逼不不不");
    }

} 