package com.etoc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
@EnableCaching//开启注解
@EnableRabbit
@MapperScan(basePackages = {"com.etoc.mapper"})
public class SpringbootCatch01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCatch01Application.class, args);
	}

}
      