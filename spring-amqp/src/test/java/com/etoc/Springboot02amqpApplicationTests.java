package com.etoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.etoc.bean.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02amqpApplicationTests {
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	AmqpAdmin amqpAdmin;

	/**
	 * 创建主键
	 */
	@Test
	public void createExchange() {
//		//创建一个Exchange组件
//	amqpAdmin.declareExchange(new DirectExchange("admin.exchange"));
//		//创建一个Queue组件
//		amqpAdmin.declareQueue(new Queue("xiaolong",true));
		// String destination, Binding.DestinationType destinationType, String exchange,
		// String routingKey, Map<String, Object> arguments
		amqpAdmin.declareBinding(
				new Binding("longer.news", Binding.DestinationType.QUEUE, "admin.exchange", "longer.news", null));
	}

	/**
	 * 点对点 direct
	 */
	@Test
	public void contextLoads() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "longer");
		map.put("phone", "27");
		// map.put("address","湖北黄冈武穴市！");
		rabbitTemplate.convertAndSend("exchange.direct", "longer.news", map);
	}

	@Test
	public void receive() {
		Object o = rabbitTemplate.receiveAndConvert("longer.news");
		System.out.println(o);
	}

	/**
	 * 广播 fanout 全部接收
	 */
	@Test
	public void sendMsg() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "longer");
		map.put("age", "27");
		map.put("address", "湖北黄冈！");
		rabbitTemplate.convertAndSend("exchange.fanout", "longer.news", map);
	}

	/**
	 * 主题 Topic Exchange的消息都会被转发到所有关联RouteKey中指定“topic”的队列
	 */
	@Test
	public void sendMsg1() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "longer");
		map.put("age", "27");
		map.put("address", "湖北黄冈！");
		rabbitTemplate.convertAndSend("exchange.topic", "longer.#", map);
	}	
}
