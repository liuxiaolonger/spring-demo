package com.longer.service.area;

import org.springframework.beans.factory.annotation.Autowired;

import com.longer.ContextHolder;
import com.longer.dao.mapper.AreaInfoMapper;
import com.longer.service.AbsService;



public abstract class AbsAreaService extends AbsService
{
	@Autowired
    protected ContextHolder context;
	
	@Autowired
	protected AreaInfoMapper areaInfoMapper;
	
	// 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
	private static final String[] transferField = {"areaId/areaId","areaType/areaType"}; 
	
	@Override
	protected String[] transferField()
	{
		return transferField;
	}
}

