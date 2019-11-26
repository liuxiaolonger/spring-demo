package com.etoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Zuul网关的主要功能为路由转发、鉴权授权和安全访问等功能
 * @author Admin
 *
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient 
@EnableDiscoveryClient 
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
	}
}