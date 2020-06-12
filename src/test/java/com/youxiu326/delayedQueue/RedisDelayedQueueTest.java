package com.youxiu326.delayedQueue;

import com.youxiu326.entity.User;
import com.youxiu326.queue.RedisDelayedQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: lihui
 * @Date: 2020-06-12 11:25
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDelayedQueueTest {

    @Autowired
    private RedisDelayedQueue redisDelayedQueue;

    @Test
    public void test01(){


        User user = new User();
        user.setId("666");
        user.setName("jddj");

        // 添加队列3秒之后执行
        redisDelayedQueue.addQueue(user,3, TimeUnit.SECONDS);

    }


}
