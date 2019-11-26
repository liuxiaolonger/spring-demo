package com.etoc.service.guser;

import org.springframework.beans.factory.annotation.Autowired;

import com.etoc.mapper.SysUserGroupUserMapper;
import com.etoc.service.AbsService;
import com.etoc.service.ContextHolder;

public abstract class AbsUGroupUserService extends AbsService
{
    @Autowired
    protected ContextHolder context;
    
    @Autowired
    protected SysUserGroupUserMapper sysUGroupUserMapper;
    
    
    public static final String USER_ID = "USER_ID";
    
    public static final String GROUP_ID = "GROUP_ID";
    
    
    protected static final String MODEL_USERID = "userId";
    
    protected static final String MODEL_GROUPID = "groupId";
    
    
 // 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
 	private static final String[] transferField = {};

 	@Override
 	protected String[] transferField() {
 		return transferField;
 	}

}
