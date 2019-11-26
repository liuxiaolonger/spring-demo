package com.etoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.config.MyselfRule;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient 
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.etoc"})
@RibbonClient(name="SPRING-API",configuration=MyselfRule.class)
public class Application {
	 public static void main(String[] args) { 
		 ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
	  }
}
