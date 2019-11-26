package com.longer.service.resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.longer.ContextHolder;
import com.longer.dao.mapper.SysResourceMapper;
import com.longer.service.AbsService;

public abstract class AbsResourceService extends AbsService
{
    @Autowired
    protected ContextHolder context;
    
    @Autowired
    protected SysResourceMapper resourceMapper;
    
    public static final String ROLE_ID = "ROLE_ID";
    
    public static final String RESOURCE_ID = "RESOURCE_ID";
    
    public static final String USER_ID = "USER_ID";
    
    public static final String POLICY_ROLE = "ROLE";
    
    public static final String POLICY_RESOURCE = "RESOURCE";
    
    public static final String POLICY_USER = "USER";
    
    protected static final String MODEL_ID = "id";
    
    protected static final String MODEL_AVAILABLE = "available";
    
    protected static final String MODEL_USERID = "userId";
    
    protected static final String MODEL_ROLEID = "roleId";
    
    protected static final String MODEL_NAME = "name";
    
    protected static final String MODEL_TYPE = "type";
    
    protected static final String MODEL_ISLEAF = "isLeaf";
    
    protected static final String MODEL_PARENTCODE = "parentCode";
    
    protected static final String MODEL_SYSTEMTYPE= "systemType";
    //默认返回查询需要的字段
    protected String[] defaultFields = {"resourceId", "code", "name", "type", "systemType", "icon", "priority", "url",
        "parentCode", "permission", "available"};
    
    // 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
    private static final String[] transferField = {"resourceId/id",};
    
    @Override
    protected String[] transferField()
    {
        return transferField;
    }
}
