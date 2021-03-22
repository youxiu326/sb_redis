package com.youxiu326.youxiu326;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @Author: lihui
 * @Date: 2021-03-22 16:33
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSetTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String keyPrefix = "redis:set";

    @Test
    public void testSet(){


        // 增加集合元素
        redisTemplate.opsForSet().add(keyPrefix,"a");
        redisTemplate.opsForSet().add(keyPrefix,"b");
        redisTemplate.opsForSet().add(keyPrefix,"c");
        redisTemplate.opsForSet().add(keyPrefix,"d");

        // 移除集合元素
        redisTemplate.opsForSet().remove(keyPrefix,"d");

        // 计算集合大小
        Long size = redisTemplate.opsForSet().size(keyPrefix);
        System.out.println(size);

        // 判断集合中是否存在某元素
        Boolean isExist = redisTemplate.opsForSet().isMember(keyPrefix, "a");

        System.out.println("a 是否存在: "+ isExist);

        Set set = redisTemplate.opsForSet().members(keyPrefix);
        System.out.println(set);

        // 随机获取集合中的元素（指定个数，不重复）
        // Object randomSet = redisTemplate.opsForSet().distinctRandomMembers(keyPrefix, 1);
        // System.out.println(randomSet);
    }


}
