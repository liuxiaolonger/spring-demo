package com.etoc.listen;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.etoc.controller.EmployeeController;
import com.rabbitmq.client.Channel;

/**
 * 发货通知
 * @author Admin
 *
 */
@Component

public class BookReceiverConsumer {
	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	Redisson redisson;
	/**
	 * 积分队列
	 */
	public static final String QUEUE_NAME_TRANSACTION = "api.book.queue";

	@Bean
	public Queue scoreQueue() {
		return new Queue(QUEUE_NAME_TRANSACTION, true);
	}

	@RabbitListener(queues = QUEUE_NAME_TRANSACTION)
	public void orderReceiver(Message message, @Headers Map<String, Object> headers, Channel channel) {
	
		String uuId = UUID.randomUUID().toString();
		RLock locks = redisson.getLock(uuId);
		try {
			locks.lock(30, TimeUnit.SECONDS);
			String msgBody = new String(message.getBody(), "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(msgBody);
			String bookId = jsonObject.getString("bookId");
			String redisBookId = redisTemplate.opsForValue().get("book_"+bookId) != null ? redisTemplate.opsForValue().get("book_"+bookId).toString()
					: "";
			if (redisBookId.equals(bookId)) {
				logger.info("正在派送中!");
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			} else {
				redisTemplate.opsForValue().set("book_"+bookId,bookId);
				String key="book_"+bookId;
				redisTemplate.expire(key, 24, TimeUnit.HOURS);
				logger.info("马上出库!");
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			}
		}catch (Exception e) {
			System.out.println("");
		} 
		finally {
			locks.unlock();
		}
	}
}
