package com.etoc.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.etoc.bean.Book;
import com.etoc.config.BookRabbitConfig;
import com.etoc.service.BookService;
/**
 * 订单类
 * @author liuxiaolong
 *
 */
@RestController
public class BookController {
	public static final Logger logger = LoggerFactory.getLogger(BookController.class);
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	Redisson redisson;
	@Autowired
	BookService service;
	// 幂等
	@PostMapping("/order/")
	public ResponseEntity<?> getOrder4(@RequestBody Book book) {
		String id = book.getBookId();
		String uuId = UUID.randomUUID().toString();
		RLock locks = redisson.getLock(uuId);
		try {
			locks.lock(30, TimeUnit.SECONDS);
			String bookId = redisTemplate.opsForValue().get(id) != null ? redisTemplate.opsForValue().get(id).toString()
					: "";
			if (id.equals(bookId)) {
				logger.info("订单已成功!");
			} else {
				redisTemplate.opsForValue().set(id, id);
				redisTemplate.expire(id, 24, TimeUnit.HOURS);
				logger.info("正在通知发货!");
				service.sendMsg(book);
			}
		} finally {
			locks.unlock();
		}
		return new ResponseEntity<Object>("end", HttpStatus.OK);
	}
}
