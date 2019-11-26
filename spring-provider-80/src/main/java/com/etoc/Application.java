package com.etoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient // ��������������Զ�ע���eureka������
@EnableDiscoveryClient // ������
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.etoc"})  
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
	}
	
}