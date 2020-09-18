package com.youxiu326.youxiu326;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: lihui
 * @Date: 2020-09-18 11:51
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestScan {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testScan(){

        String str = "abc";
        for (int i = 0; i < 249; i++) {
            redisTemplate.opsForValue().set(str+i,i);
        }

        Set<String> keys = scan(str);
        System.out.println(keys);


    }

    public Set<String> scan(String matchKey) {
        Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(matchKey + "*").count(100).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });
        return keys;
    }

}
