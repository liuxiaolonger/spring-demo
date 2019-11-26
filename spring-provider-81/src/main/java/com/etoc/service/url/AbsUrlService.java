package com.etoc.service.url;

import org.springframework.beans.factory.annotation.Autowired;

import com.etoc.mapper.SysUrlFilterMapper;
import com.etoc.service.AbsService;
import com.etoc.service.ContextHolder;

public abstract class AbsUrlService extends AbsService
{
    @Autowired
    protected ContextHolder context;
    
    @Autowired
    protected SysUrlFilterMapper sysUrlFilterMapper;
    
    protected static final String MODEL_NAME = "name";
    
    //默认查询返回的字段
    protected String[] defaultFields = {"urlFilterId", "name", "method", "url", "roles", "permissions"};
    
    // 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
    private static final String[] transferField = {"urlFilterId/id",};
    
    @Override
    protected String[] transferField()
    {
        return transferField;
    }
}
