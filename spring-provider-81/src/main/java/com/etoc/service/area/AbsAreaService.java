package com.etoc.service.area;

import org.springframework.beans.factory.annotation.Autowired;

import com.etoc.mapper.AreaInfoMapper;
import com.etoc.service.AbsService;
import com.etoc.service.ContextHolder;

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

