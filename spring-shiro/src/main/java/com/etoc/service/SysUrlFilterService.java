package com.etoc.service;

import java.util.List;

import com.etoc.model.SysUrlFilter;


/**
 * 动态url权限控制
 * 
 * @author longlong
 * @version [版本号, 2018年8月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface SysUrlFilterService {
    
	// 根据ID查询动态url权限的信息
	SysUrlFilter selectBySysUrlId(String id);

	// 查询所有url和角色权限关系
	List<SysUrlFilter> listAll();
	
	// 根据url路径和请求类型查询权限
	SysUrlFilter selectByUrl(String httpUri, String httpMethod);

}
