package com.huarui;

import com.huarui.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

	//1 注入spring 封装好的 redis客户端 之 StringRedisTemplate（专业存取字符串）
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	//2 注入spring 封装好的 redis客户端 之 RedisTemplate（专业存取对象）
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
		//操作 String  key val1
		ValueOperations<String, String> vos =  stringRedisTemplate.opsForValue();
		vos.set("key1","牛逼牛逼");
		System.out.println("-------------"+vos.get("key1"));

		HashOperations<String, String, String> hvos = stringRedisTemplate.opsForHash();
		hvos.put("keyList","key02","哈哈哈哈哈擦擦擦");
		System.out.println(hvos.get("keyList","key02"));


		Long delete = hvos.delete("keyList", "key02");

		Boolean exists = redisTemplate.hasKey("key1");
		if(exists){
			System.out.println("存在key");
		}else{
			System.out.println("不存在key");
		}

		exists = stringRedisTemplate.hasKey("key1");
		if(exists){
			System.out.println("存在key");
		}else{
			System.out.println("不存在key");
		}
	}

	@Test
	public void testSaveUser() throws Exception {

		HashOperations<String, String, User> hvos =redisTemplate.opsForHash();

		User user = new User("001","牛逼你不","123456789",187,new Date());
		hvos.put("userList",user.getId(),user);
		User user2 = new User("002","超级储存","1234568789",197,new Date());
		hvos.put("userList",user2.getId(),user2);

		User getUser01 = (User) redisTemplate.opsForHash().get("userList", user.getId());
		User getUser02 = (User) redisTemplate.opsForHash().get("userList", user2.getId());
		//System.out.println("\r\n"+getUser01);
		//System.out.println("\r\n"+getUser02);
	}

}
