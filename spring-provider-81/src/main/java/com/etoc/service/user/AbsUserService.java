package com.etoc.service.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.etoc.mapper.SysUserMapper;
import com.etoc.service.AbsService;
import com.etoc.service.ContextHolder;

public abstract class AbsUserService extends AbsService {
	// 结果为用户
	public static final String POLICY_USER = "USER";
	// 结果为角色
	public static final String POLICY_ROLE = "ROLE";
	// 结果为用户组
	public static final String POLICY_GROUP = "GROUP";
	// 结果为资源
	public static final String POLICY_RESOURCE = "RESOURCE";
	
	
	// 重置用户密码
	public static final String POLICY_USERPwd = "USERPwd";
	// 通过登录名验证
	public static final String POLICY_USERNAME = "USERNAME";

	// 设置mapper层字段  用户id
	protected static final String MODEL_ID = "id";
	// 设置mapper层字段	用户组id
	protected static final String MODEL_GROUPID = "userGroupId";
	// 设置mapper层字段	用户组id
	protected static final String MODEL_ROLEID = "roleId";
	// 设置mapper层字段	用户登录名
	protected static final String MODEL_LOGINNAME = "loginName";
	// 设置mapper层字段	用户名
	protected static final String MODEL_USERNAME = "userName";
	// 设置mapper层字段	组织机构id
	protected static final String MODEL_ORGANIZATIONID = "organizationId";
	// 设置mapper层字段	用户状态
	protected static final String MODEL_STATUS = "status";
	
	
	// 重置密码
	protected static final String RESET_PASSWORD = "123456";
	
	@Autowired
	protected ContextHolder context;

	@Autowired
	protected SysUserMapper userMapper;

	// 需要互相映射的字段("/"分隔,前面是resource字段名,后面是model字段名)
	private static final String[] transferField = {"userId/id","userPhone/userMobile"};

	@Override
	protected String[] transferField() {
		return transferField;
	}

}
