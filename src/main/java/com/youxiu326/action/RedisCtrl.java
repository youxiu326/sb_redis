package com.youxiu326.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisCtrl {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

} 