package com.youxiu326.queue;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * @Link https://blog.csdn.net/ZuiChuDeQiDian/article/details/104374110
 * @Description: 延时队列
 */
@Service
public class RedisDelayedQueue {

    @Autowired
    RedissonClient redissonClient;

    /**
     * offer 插入失败返回false
     * put   阻塞，直到可用空间才插入
     *
     *
     * poll  取元素
     * take  阻塞，直到有元素可取
     */


    /**
     * 添加队列
     *
     * @param t        DTO传输类
     * @param delay    时间数量
     * @param timeUnit 时间单位
     * @param <T>      泛型
     */
    public <T> void addQueue(T t, long delay, TimeUnit timeUnit) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue("youxiu326_delayed_queue");
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, delay, timeUnit);
        // delayedQueue.destroy();
    }


}
