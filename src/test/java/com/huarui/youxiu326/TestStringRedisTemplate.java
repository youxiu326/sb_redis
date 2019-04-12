package com.huarui.youxiu326;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStringRedisTemplate {

    static final Logger LOGGER = LoggerFactory.getLogger(TestStringRedisTemplate.class);

    //注入spring 封装好的 redis客户端 之 StringRedisTemplate（专业存取字符串）
    @Autowired
    private StringRedisTemplate template;

    private static String commonKey = "commonKey";
    private static String commonValue = "commonValue";

    private static String commonKeyNew = "commonKeyNew";
    private static String commonValueNew = "commonValueNew";
    private static Integer commonTime = 15;

    private static String memberKey = "memberKey";
    private static String memberValue = "12";


    /**
     * 基本增删改查
     */
    @Test
    public void testBaseHandle(){

        //操作 String  key val1
        ValueOperations<String, String> vos =  template.opsForValue();

        //1.新增 key value
        vos.set(commonKey,commonValue);
        //2.根据 key 查询 value
        String returnValue = vos.get(commonKey);
        //3.查看 key 是否存在
        Boolean exists = template.hasKey(commonKey);
        //4.修改 key value
        vos.set(commonKey,commonValue+":new");
        //5.根据key 删除 value
        template.delete(commonKey);
    }


    /**
     * 复杂操作
     */
    @Test
    public void testComplexHandle(){

        //操作 String  key val1
        ValueOperations<String, String> vos =  template.opsForValue();

        //1.设置 key value key过期时间
        vos.set(commonKeyNew,commonValueNew,commonTime, TimeUnit.SECONDS);

        //2.根据key获取过期时间
        Long expire = template.getExpire(commonKeyNew);
        //3.根据key获取过期时间并换算成指定单位
        Long expireNew = template.getExpire(commonKeyNew, TimeUnit.SECONDS);

        //4.对value进行运算

        vos.set(memberKey,memberValue);
        LOGGER.info(vos.get(memberKey));
        //value 减1
        template.boundValueOps(memberKey).increment(-1);
        LOGGER.info(vos.get(memberKey));
        //value 加1
        template.boundValueOps(memberKey).increment(1);
        LOGGER.info(vos.get(memberKey));

        //5.追加
        LOGGER.info(vos.get(commonKey));
        vos.append(commonKey,commonValue);
        LOGGER.info(vos.get(commonKey));
        vos.append(commonKey+":append",commonValue);
        LOGGER.info(vos.get(commonKey+":append"));

    }

} 