package com.longer.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.longer.dao.model.SystemLog;

@Mapper
public interface SystemLogMapper {

	int insertAll(SystemLog record);

	int insertSelective(SystemLog record);

	// 根据条件查询系统操作日志
	List<SystemLog> selectByQuery(Map<String, Object> query);
}