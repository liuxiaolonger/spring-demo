package com.longer.service.userRole;

import org.springframework.beans.factory.annotation.Autowired;

import com.longer.ContextHolder;
import com.longer.dao.mapper.SysUserRoleMapper;
import com.longer.service.AbsService;

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
