package com.youxiu326.action;

import com.youxiu326.entity.User;
import com.youxiu326.queue.RedisDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Controller
public class RedisCtrl {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisDelayedQueue redisDelayedQueue;

    /**
     * redis发布
     * @return
     */
    @RequestMapping("/test")
    public @ResponseBody String test(){
        /**
         * 通过chat通道发送了一条消息
         */
        stringRedisTemplate.convertAndSend("chat", "牛逼不不不");
        return "ok";
    }

    /**
     * <p>
     *     http://localhost:8080/send?id=6379&name=lihui&delay=10
     *     http://localhost:8080/send?id=3306&name=longcaifang&delay=6
     * </p>
     *
     * 延时消息
     * @return
     */
    @RequestMapping("/send")
    public @ResponseBody String send(User user,Long delay){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format1 = dateTimeFormatter.format(now);
        redisDelayedQueue.addQueue(user,delay, TimeUnit.SECONDS);
        System.out.println(format1 + "->添加延时队列："+user);
        return "ok";
    }

} 