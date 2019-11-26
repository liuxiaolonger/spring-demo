package com.etoc.service.roleRes;

import org.springframework.beans.factory.annotation.Autowired;

import com.etoc.mapper.SysRoleResourceMapper;
import com.etoc.service.AbsService;
import com.etoc.service.ContextHolder;

public abstract class AbsRoleResourceService extends AbsService
{
    @Autowired
    protected ContextHolder context;
    
    @Autowired
    protected SysRoleResourceMapper sysRoleResourceMapper;
    
    public static final String ROLE_ID = "ROLE_ID";
    
    public static final String RESOURCE_ID = "RESOURCE_ID";
    
    protected static final String MODEL_ROLEID = "roleId";
    
    protected static final String MODEL_RESOURCEID = "resourceId";
    
    protected static final String MODEL_ISLEAF = "isLeaf";
    
    // 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
    private static final String[] transferField = {};
    
    @Override
    protected String[] transferField()
    {
        return transferField;
    }
}
