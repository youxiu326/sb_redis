package com.huarui.youxiu326;

import com.huarui.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTemplate {

    static final Logger LOGGER = LoggerFactory.getLogger(TestRedisTemplate.class);

    //2 注入spring 封装好的 redis客户端 之 RedisTemplate（专业存取对象）
    @Autowired
    private RedisTemplate redisTemplate;

    private static String commonKey = "commonKey";
    private static User commonValue = new User("youxiu326","lihui","176707456**",170,new Date());

    private static String commonKeyNew = "commonKeyNew";
    private static String commonValueNew = "commonValueNew";
    private static Integer commonTime = 15;

    private static String hKey = "hKey";
    private static String hhKey = "hhKey";

    @Test
    public void testBaseHandle(){

        ValueOperations<String, User> hvos =redisTemplate.opsForValue();
        //1.新增 key value
        hvos.set(commonKey,commonValue);
        //2.根据 key 查询 value
        User returnValue = hvos.get(commonKey);
        //3.查看 key 是否存在
        Boolean exists = redisTemplate.hasKey(commonKey);
        //4.修改 key value
        hvos.set(commonKey,new User("is ok","are you ok","176707456**",170,new Date()));
        //5.根据key 删除 value
        redisTemplate.delete(commonKey);
    }

    @Test
    public void testBaseHandleForHash(){

        HashOperations<String,String,User> hvos = redisTemplate.opsForHash();
        //1.新增 key value
        hvos.put(hKey,hhKey,commonValue);
        //2.根据 key 查询 value
        User returnValue = hvos.get(hKey,hhKey);
        //3.查看 key 是否存在
        Boolean exists = redisTemplate.hasKey(commonKey);
        //4.修改 key value
        hvos.put(hKey,hhKey,new User("is ok","are you ok","176707456**",170,new Date()));
        //5.根据key 删除 value
        redisTemplate.delete(commonKey);
    }

    /**
     * 复杂操作
     */
    @Test
    public void testComplexHandle(){
        HashOperations<String,String,User> hvos = redisTemplate.opsForHash();

        //1.设置过期时间
        //redis没有提供hsetex（）这样的方法，redis中过期时间只针对顶级key类型，对于hash类型是不支持的

        hvos.put(hKey+":"+hhKey,hhKey,commonValue);

        //2.根据key获取过期时间
        Long expire = redisTemplate.getExpire(hKey+":"+hhKey);
        //3.根据key获取过期时间并换算成指定单位
        Long expireNew = redisTemplate.getExpire(hKey+":"+hhKey, TimeUnit.SECONDS);

    }


} 