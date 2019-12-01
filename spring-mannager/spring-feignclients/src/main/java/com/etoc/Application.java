package com.etoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient 
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.etoc"})
@EnableFeignClients(basePackages = {"com.etoc.config"})
public class Application {
	 public static void main(String[] args) { 
		 ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
	  }
}
