package com.huarui.config;

import org.springframework.stereotype.Component;

/**
 * 消息处理器POJO
 * redis订阅
 */
@Component
public class MessageReceiver {

    /**接收消息的方法*/
    public void receiveMessage(String message,String chanel){
        System.out.println("收到一条消息："+message);
        System.out.println("通道名称："+chanel);
    }

} 