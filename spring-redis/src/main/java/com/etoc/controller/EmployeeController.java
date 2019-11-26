package com.etoc.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.model.Employee;
import com.etoc.service.EmployeeService;

@RestController
public class EmployeeController {
	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	Redisson redisson;
	@Autowired
	private EmployeeService service;

	@GetMapping("/emp/{id}/")
	public ResponseEntity<?> get(@PathVariable("id") Integer id) {
		return new ResponseEntity<Object>(service.selectByPrimaryKey(id), HttpStatus.OK);
	}

	@PutMapping("/emp/")
	public ResponseEntity<?> update(@RequestBody Employee emp) {
		return new ResponseEntity<Object>(service.updateByPrimaryKeySelective(emp), HttpStatus.OK);
	}

	@DeleteMapping("/emp/{id}/")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		return new ResponseEntity<Object>(service.deleteByPrimaryKey(id), HttpStatus.OK);
	}

	// 单机版
	@GetMapping("/order/{id}/")
	public ResponseEntity<?> getOrder(@PathVariable("id") Integer id) {
		synchronized (this) {
			Integer lock = Integer.parseInt(redisTemplate.opsForValue().get("lock").toString());
			if (lock > 0) {
				Integer reLock = lock - 1;
				redisTemplate.opsForValue().set("lock", reLock + "");
				logger.info("抢到了第" + lock);
			} else {
				logger.info("票已经抢完");
			}
		}
		return new ResponseEntity<Object>("end", HttpStatus.OK);
	}

	// 当前进程上锁后挂了会带来问题
	@GetMapping("/order1/{id}/")
	public ResponseEntity<?> getOrder1(@PathVariable("id") Integer id) {
		UUID randomUUID = UUID.randomUUID();
		Boolean setIfAbsent = redisTemplate.opsForValue().setIfAbsent("lockOrder", randomUUID);
		try {
			if (setIfAbsent) {
				Integer lock = Integer.parseInt(redisTemplate.opsForValue().get("lock").toString());
				if (lock > 0) {
					Integer reLock = lock - 1;
					redisTemplate.opsForValue().set("lock", reLock + "");
					logger.info("抢到了第" + lock);
				} else {
					logger.info("票已经抢完");
				}
			} else {
				logger.info("系统异常,稍后再试");
				return new ResponseEntity<Object>("error", HttpStatus.OK);
			}
		} finally {
			redisTemplate.delete("lockOrder");
		}
		return new ResponseEntity<Object>("end", HttpStatus.OK);
	}

	// 当前进程上锁后挂了会带来问题
	@GetMapping("/order2/{id}/")
	public ResponseEntity<?> getOrder2(@PathVariable("id") Integer id) {
		UUID randomUUID = UUID.randomUUID();
		Boolean setIfAbsent = redisTemplate.opsForValue().setIfAbsent("lockOrder", randomUUID);
		redisTemplate.expire("lockOrder", 10, TimeUnit.SECONDS);
		try {
			if (setIfAbsent) {
				Integer lock = Integer.parseInt(redisTemplate.opsForValue().get("lock").toString());
				if (lock > 0) {
					Integer reLock = lock - 1;
					redisTemplate.opsForValue().set("lock", reLock + "");
					logger.info("抢到了第" + lock);
				} else {
					logger.info("票已经抢完");
				}
			} else {
				logger.info("系统异常,稍后再试");
				return new ResponseEntity<Object>("error", HttpStatus.OK);
			}
		} finally {
			if (randomUUID.equals(redisTemplate.opsForValue().get("lockOrder").toString())) {
				redisTemplate.delete("lockOrder");
			}
		}
		return new ResponseEntity<Object>("end", HttpStatus.OK);
	}

	// 当前进程上锁后挂了会带来问题
	@GetMapping("/order3/{id}/")
	public ResponseEntity<?> getOrder3(@PathVariable("id") Integer id) {
		String order = UUID.randomUUID().toString();
		RLock locks = redisson.getLock(order);
		try {
			locks.lock(30, TimeUnit.SECONDS);
			Integer lock = Integer.parseInt(redisTemplate.opsForValue().get("lock").toString());
			if (lock > 0) {
				Integer reLock = lock - 1;
				redisTemplate.opsForValue().set("lock", reLock + "");
				logger.info("抢到了第" + lock);
			} else {
				logger.info("票已经抢完");
			}
		} finally {
			locks.unlock();
		}
		return new ResponseEntity<Object>("end", HttpStatus.OK);
	}

	//幂等
	@GetMapping("/order4/{id}/")
	public ResponseEntity<?> getOrder4(@PathVariable("id") String id) {
		String uuId = UUID.randomUUID().toString();
		RLock locks = redisson.getLock(uuId);
		try {
			locks.lock(30, TimeUnit.SECONDS);
			String order=redisTemplate.opsForValue().get(id)!=null?redisTemplate.opsForValue().get(id).toString():"";
			if (id.equals(order)) {
				logger.info("订单已经发货了!");
			} else {
				redisTemplate.opsForValue().set(id, id);
				redisTemplate.expire(id, 24, TimeUnit.HOURS);
				logger.info("好的马上给你发货!");
			}
		} finally {
			locks.unlock();
		}
		return new ResponseEntity<Object>("end", HttpStatus.OK);
	}
}
