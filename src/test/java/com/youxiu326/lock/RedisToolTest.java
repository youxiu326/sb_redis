package com.youxiu326.lock;

import com.youxiu326.util.RedisTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: lihui
 * @Date: 2020-09-24 17:09
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisToolTest {

    @Autowired
    private RedisTool redisTool;


    @Test
    public void testLock(){


        boolean lock = redisTool.lock("redis:key", "1", 60);
        if (lock){
            System.out.println("加锁成功");
        }else {
            System.out.println("加锁失败");
        }


        boolean unLock = redisTool.unLock("redis:key", "2");
        if (unLock){
            System.out.println("解锁成功");
        }else {
            System.out.println("解锁失败");
        }

        System.out.println("=========测试结束");

    }


}
