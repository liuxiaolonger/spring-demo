package com.etoc.service.userRole;

import org.springframework.beans.factory.annotation.Autowired;

import com.etoc.mapper.SysUserRoleMapper;
import com.etoc.service.AbsService;
import com.etoc.service.ContextHolder;


public abstract class AbsUserRoleService extends AbsService {
	@Autowired
	protected ContextHolder context;

	@Autowired
	protected SysUserRoleMapper sysUserRoleMapper;

	public static final String USER_ID = "USER_ID";

	public static final String ROLE_ID = "ROLE_ID";
	
	
	protected static final String MODEL_USERID = "userId";
	
	protected static final String MODEL_ROLEID = "roleId";
	

	// 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
	private static final String[] transferField = {};

	@Override
	protected String[] transferField() {
		return transferField;
	}

}
