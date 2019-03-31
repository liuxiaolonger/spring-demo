package com.etoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EurekaServer服务器端启动类,接受其它微服务注册进来
 * 
 * @author zhouyang
 */
@SpringBootApplication
@EnableEurekaServer // EurekaServer服务器端启动类,接受其它微服务注册进来
public class EurekaServerApplication2
{
	public static void main(String[] args)
	{
		SpringApplication.run(EurekaServerApplication2.class, args);
	}
}
