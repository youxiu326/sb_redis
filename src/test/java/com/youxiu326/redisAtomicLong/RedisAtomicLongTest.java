package com.youxiu326.redisAtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisAtomicLongTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 方法一：
     *
     * public long get();//返回当前的值
     *
     *
     * 方法二：
     *
     * public void set(long newValue);//设置当前实例的值为newValue
     *
     *
     * 方法三：
     *
     * public long incrementAndGet()；//将当前实例的key值加一并且返回
     *
     *
     */
    @Test
    public void test(){

        RedisAtomicLong redisAtomicLong = new RedisAtomicLong("orderKey",redisTemplate.getConnectionFactory());


        // 第一次，设置初始值
        long original = 0L;

        // 获取 code 值
        original = redisAtomicLong.get();
        System.out.println("*****************original:"+original);

        // 第一次，设置初始值
        if (original == 0L) {
            redisAtomicLong.set(5L);
        }

        //获得加1后的值
        long now = redisAtomicLong.incrementAndGet();
        System.out.println("*****************now:"+now);

    }

    private int maxSerial = Integer.MAX_VALUE;

    /**
     * 用Redis计数器生成订单号,生成规则:前缀+年月日+Redis自增长序列号
     * 劣势：依赖于Redis,消耗带宽,消耗流量.
     * 优势：保证有序,而且每天的订单号增长都是从1开始自增的
     * @return 订单号 eg:Order201906110002
    作者：进阶中的码农
    链接：https://www.jianshu.com/p/bed1dca7be8c
    来源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    @Test
    public void test02(){

        // 编号前缀
        String prefix = "Order";

        RedisAtomicLong redisAtomicLong = new RedisAtomicLong("orderKey",redisTemplate.getConnectionFactory());

        long num = redisAtomicLong.incrementAndGet();
        if(num == 1) {
            Date today=new Date();
            Calendar cs=Calendar.getInstance();
            cs.setTime(today);
            //凌晨过期
            cs.add(Calendar.DAY_OF_MONTH,1);
            cs.set(Calendar.HOUR_OF_DAY,0);
            cs.set(Calendar.MINUTE,0);
            cs.set(Calendar.SECOND,0);
            //设置第二天的凌晨00：00为失效期
            redisAtomicLong.expireAt(cs.getTime());
        }

        if(num==maxSerial) {
            num = redisAtomicLong.addAndGet(-maxSerial);
        }

        //当前时间
        LocalDateTime date = LocalDateTime.now();
        String time = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        //生成编号
        String code = String.format("%s%s%s", prefix,time,num);

        System.out.println(code);

    }

}
