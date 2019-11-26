package com.etoc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 
 * @EnableDiscoveryClient eureka注册中心客服端标识  @EnableEurekaClient同样的效果
 *
 */
@SpringBootApplication
@MapperScan(value = "com.etoc.mapper")
@EnableEurekaClient 
@EnableDiscoveryClient
public class ShiroApplication {
//(basePackages={"channel.security.server","org.channel.common"})
	public static void main(String[] args) {
		SpringApplication.run(ShiroApplication.class, args);
	}
}
