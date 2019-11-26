package com.etoc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoc.mapper.SysUrlFilterMapper;
import com.etoc.model.SysUrlFilter;
import com.etoc.service.SysUrlFilterService;


/**
 * 动态url权限控制
 * 
 * @author longlong
 * @version [版本号, 2018年8月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class SysUrlFilterServiceImpl implements SysUrlFilterService {

	@Autowired
	private SysUrlFilterMapper sysUrlFilterMapper;

	/** {@inheritDoc} */

	@Override
	public SysUrlFilter selectBySysUrlId(String id) {
		SysUrlFilter sysUrlFilter = sysUrlFilterMapper.selectBySysUrlId(id);
		return sysUrlFilter;
	}

	@Override
	public List<SysUrlFilter> listAll() {
		return sysUrlFilterMapper.selectAllSysUrl();
	}

	/** {@inheritDoc} */
	 
	@Override
	public SysUrlFilter selectByUrl(String httpUri, String httpMethod) {
		Map<String, Object> map = new HashMap<>();
		map.put("url", httpUri);
		map.put("method", httpMethod);
		List<SysUrlFilter> list = sysUrlFilterMapper.selectSysUrl(map);
		if(CollectionUtils.isEmpty(list))return null;
		SysUrlFilter url = list.get(0);
		return url;
	}
}
