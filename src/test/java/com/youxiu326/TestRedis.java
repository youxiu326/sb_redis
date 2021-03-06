package com.youxiu326;

import com.youxiu326.entity.User;
import com.youxiu326.util.PageResult;
import com.youxiu326.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

		System.out.println(hvos.get("userList",user.getId()));
		System.out.println(hvos.get("userList",user2.getId()));

		User getUser01 = (User) redisTemplate.opsForHash().get("userList", user.getId());
		User getUser02 = (User) redisTemplate.opsForHash().get("userList", user2.getId());
		//System.out.println("\r\n"+getUser01);
		//System.out.println("\r\n"+getUser02);
	}

	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void testScan() throws Exception {

		ValueOperations<String, String> vos = stringRedisTemplate.opsForValue();
		for (int i = 1; i < 101; i++) {
		//	vos.set("scan_key_"+i, "scan_val_"+i);
		}

		int currentPage = 1;
		int pageSize = 50;
		List<String> rows = new ArrayList<>();
		rows.add("start");
		while (rows.size()>0){
			PageResult<String> keys = redisUtil.findKeysForPage("scan_key_*", currentPage++, pageSize);
			rows = keys.getRows();
			Long total = keys.getTotal();
			if (rows.size()>0){
				System.out.println("======total:"+total);
				for (String key : rows) {
					System.out.println(key);
				}
			}
		}


	}

}
