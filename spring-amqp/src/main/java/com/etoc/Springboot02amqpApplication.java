package com.etoc;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 1.RabbitTemplate 连接和发送接收消息
 * 2.AmqpAdmin  RabbitMQ系统管理功能组件  创建和删除  exchange queues
 */
@EnableRabbit
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Springboot02amqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot02amqpApplication.class, args);
	}

}
