package com.longer.service.group;

import org.springframework.beans.factory.annotation.Autowired;

import com.longer.ContextHolder;
import com.longer.dao.mapper.SysUserGroupMapper;
import com.longer.service.AbsService;

public abstract class AbsUserGroupService extends AbsService {

	// 结果对象为用户组
	protected static final String POLICY_GROUP = "GROUP";
	// 结果对象为用户
	protected static final String POLICY_USER = "USER";
	// 用户主键id
	protected static final String POLICY_USERID = "userId";
	// 用户组状态
	protected static final String POLICY_STATUS = "status";
	
	protected static final String MODEL_ID = "id";

	@Autowired
	protected ContextHolder context;

	@Autowired
	protected SysUserGroupMapper userGroupMapper;
	
	

	// 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
	private static final String[] transferField = { "userGroupId/id", "userGroupName/groupName", "userGroupDesc/description","state/available" };

	@Override
	protected String[] transferField() {
		return transferField;
	}

}
