package com.huarui.youxiu326;

import com.huarui.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTemplateOfList {
    static final Logger LOGGER = LoggerFactory.getLogger(TestTemplateOfList.class);

    //注入spring 封装好的 redis客户端 之 RedisTemplate（专业存取对象）
    @Autowired
    private RedisTemplate redisTemplate;

    private static String commonKey = "commonKey";
    private static User commonValue = new User("youxiu326","lihui","176707456**",170,new Date());

    private List<User> list = new ArrayList<>();

    @Before
    public void before(){
        for (int i = 0; i < 10; i++) {
            User user = new User("youxiu326:"+i,"lihui:"+i,"176707456*"+i,170,new Date());
            list.add(user);
        }
    }

    /**
     * 操作list集合
     * Redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
     */
    @Test
    public void testBaseHandleForList(){
        ListOperations lvos = redisTemplate.opsForList();

        //1.左边添加元素
        lvos.leftPush(commonKey,commonValue);
        //2.左边批量添加元素
        lvos.leftPushAll(commonKey,list);
        //3.如果存在集合则添加元素
        lvos.leftPushIfPresent(commonKey,list.get(0));
        //4.右边添加元素
        lvos.rightPush(commonKey,list.get(9));

        //5.获取元素长度
        Long size = lvos.size(commonKey);
        LOGGER.info(commonKey+" size: "+ size);

        User index = (User) lvos.index(commonKey,1);
        LOGGER.info(index.toString());
    }

    /**
     * 复杂操作
     */
    @Test
    public void testComplexHandle(){

        ListOperations lvos = redisTemplate.opsForList();

        LOGGER.info(lvos.size(commonKey).toString());

        User noRemoveUser = (User) lvos.index(commonKey,0);

        LOGGER.info("还未被移除的元素: "+ noRemoveUser.toString());

        User removeUser = (User) lvos.leftPop(commonKey);

        LOGGER.info("被移除的元素: "+ removeUser.toString());

        LOGGER.info(lvos.size(commonKey).toString());

        noRemoveUser = (User) lvos.index(commonKey,0);

        LOGGER.info("最左边的元素: "+ noRemoveUser.toString());


    }

} 