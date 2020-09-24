package com.youxiu326.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import java.util.Collections;

/**
 * @Auther: lihui
 * @Date: 2020-09-24 17:03
 * @Description: redis分布式锁
 */
@Component
public class RedisTool {


    @Autowired
    RedisTemplate redisTemplate;

    private static final Long SUCCESS = 1L;

    /**
     * 获取锁
     * @param lockKey 锁🔐
     * @param value   存入的值
     * @param expireTime：单位-秒
     */
    public boolean lock(String lockKey, String value, int expireTime){

        String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";

        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),value,expireTime);

        if(SUCCESS.equals(result)){
            return true;
        }

        return false;
    }

    /**
     * 释放锁
     * @param lockKey 锁🔐
     * @param value   存入的值(解锁的时候如果值相同才删除锁)
     */
    public boolean unLock(String lockKey, String value){

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),value);
        if(SUCCESS.equals(result)) {
            return true;
        }

        return false;
    }

}
