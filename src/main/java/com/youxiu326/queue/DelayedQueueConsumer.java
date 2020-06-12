package com.youxiu326.queue;

import com.youxiu326.entity.User;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: lihui
 * @Date: 2020-06-12 11:56
 * @Description: 延时队列消费者
 */
@Component
public class DelayedQueueConsumer extends Thread{

    @Autowired
    RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        this.start();
    }

    @Override
    public void run() {
        RBlockingQueue<User> blockingFairQueue = redissonClient.getBlockingQueue("youxiu326_delayed_queue");
        while(true) {
            try {
                // 获取队列
                User user = blockingFairQueue.take();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String format1 = dateTimeFormatter.format(now);
                System.out.println(format1 + "->消费延时队列："+user);
            } catch (InterruptedException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }
        }
    }


}
