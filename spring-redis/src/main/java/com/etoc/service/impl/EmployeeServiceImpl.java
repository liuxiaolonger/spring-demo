package com.etoc.service.impl;

import com.etoc.mapper.EmployeeMapper;
import com.etoc.model.Employee;
import com.etoc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "emp")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeMapper mapper;
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;

	/**
	 * CacheEvicts删除缓存 allEntries:指定删除这个缓存中的所有数据 beforeInvocation:是否在方法之前执行
	 * 
	 * @param id
	 * @return
	 */
	@CacheEvict(value = "emp", key = "#id")
	public int deleteByPrimaryKey(Integer id) {
		System.out.println("删除员工" + id);
		mapper.deleteByPrimaryKey(id);
		return 0;
	}

	@Override
	public int insert(Employee record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Employee record) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * cacheNames 放在哪个组件中，既哪个缓存中。可以是多个 key 规则可以指定 key
	 * ="#root.methodName+'['+#id+']'" keyGenerator 自定义命名规则 condition 满足缓存 unless
	 * 满足不缓存
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(cacheNames = { "emp" }/* ,keyGenerator ="myKeyGenerator" ,condition = "#id>13",unless = "#a0!=123" */)
	public Employee selectByPrimaryKey(Integer id) {
		System.out.println("查询" + id + "员工");
           
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * CachePut :即调用方法；又更新缓存数据
	 * 
	 * @param record
	 * @return
	 */
	@CachePut(cacheNames = { "emp" }, key = "#record.id")
	public Employee updateByPrimaryKeySelective(Employee record) {
		System.out.println("更新" + record.getId() + "员工");
		mapper.updateByPrimaryKey(record);
		return record;
	}

	@Caching(cacheable = { @Cacheable(cacheNames = {
			"emp" }/* ,keyGenerator ="myKeyGenerator" ,condition = "#id>13",unless = "#a0!=123" */) }, put = {
					@CachePut(cacheNames = { "emp" }, key = "#record.id") }, evict = {
							@CacheEvict(value = "emp", key = "#id") })
	public int updateByPrimaryKey(Employee record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
