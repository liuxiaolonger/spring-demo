package com.etoc.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Admin on 2019/4/18.
 */
@Configuration
public class RedissionConfig {
	/**
	 * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
	 * 
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public Redisson redisson() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://192.168.1.10:6379").setDatabase(0);
		return (Redisson) Redisson.create(config);
	}

}
