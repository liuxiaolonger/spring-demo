package com.etoc.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

@Component
public class ReBookService {
	public static final Logger logger = LoggerFactory.getLogger(BookService.class);
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	Redisson redisson;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	/**
	 * 补单队列
	 */
	public static final String CREATE_QUEUE_NAME_TRANSACTION = "api.book.reCreate.queue";

	/**
	 * @param message
	 * @param headers
	 * @param channel
	 */
	@RabbitListener(queues = CREATE_QUEUE_NAME_TRANSACTION)
	public void orderReceiver(Message message, @Headers Map<String, Object> headers, Channel channel) {
		String uuId = UUID.randomUUID().toString();
		RLock locks = redisson.getLock(uuId);
		try {
			locks.lock(30, TimeUnit.SECONDS);
			  String msgBody = new String(message.getBody(), "UTF-8");
	          JSONObject jsonObject = JSONObject.parseObject(msgBody);
	          String bookId = jsonObject.getString("bookId");
			  String order = redisTemplate.opsForValue().get("book_"+bookId) != null ? redisTemplate.opsForValue().get("book_"+bookId).toString(): "";
			if (bookId.equals(order)) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} else {
				 rabbitTemplate.send(message);
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}		
		finally {
			locks.unlock();
		}
	}
}
