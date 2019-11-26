package com.etoc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02amqpApplicationTests {
	@Autowired
	RedisTemplate<Object, Object>  redisTemplate;
	@Test
	public void sendMsg1() {
		redisTemplate.opsForValue().set("lock","100");//操作字符串
//		redisTemplate.opsForHash();//操作hash
//		redisTemplate.opsForList();//操作list
//		redisTemplate.opsForSet();//操作set
//		redisTemplate.opsForZSet();//操作有序set
	}
}
