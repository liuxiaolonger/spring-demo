package com.etoc.service.user.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.base.BaseService;
import com.etoc.base.SysQueryService;
import com.etoc.base.SysSaveService;
import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.etoc.service.group.impl.UserGroupFacade;
import com.etoc.service.guser.AbsUGroupUserService;
import com.etoc.service.guser.impl.UGroupUserFacade;
import com.etoc.service.resource.impl.ResourceServiceFacade;
import com.etoc.service.role.impl.RoleServiceFacade;
import com.etoc.service.user.AbsUserService;
import com.etoc.service.user.UserService;
import com.etoc.service.user.vo.SysUsers;
import com.etoc.service.userRes.AbsUserResourceService;
import com.etoc.service.userRes.impl.UserResourceFacade;
import com.etoc.service.userRole.AbsUserRoleService;
import com.etoc.service.userRole.impl.UserRoleFacade;
import com.etoc.vo.StateInstance;

/**
 * 
 * 用户调度器
 * @author  longlong
 * @version  [版本号, 2019年1月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserServiceFacade extends AbsUserService implements UserService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String userId;//用户主键id

	private String userName;//用户名称

	private String loginNameLike;//分页模糊查询的登录名称
	
	private String loginName;//验证登录名称

	private String groupId;//用户组主键id

	private String roleId;//角色主键id

	private String organizationId;//组织机构主键id
	
	private String status;//用户状态

	private String policyName;//策略名称
	
	private String[] fields;//返回字段

	private SysUsers sysUsers;//用vo对象
	
	private Integer isLeaf;//资源是否是叶子节点 1.是，0.否
	
	private String available;// 资源状态


	@Override
	public UserServiceFacade addFields(String[] fields) {
		this.fields = fields;
		return this;
	}
	
	@Override
	public UserServiceFacade addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public UserServiceFacade addUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public UserServiceFacade addLoginNameLike(String loginNameLike) {
		this.loginNameLike = loginNameLike;
		return this;
	}
	
	public UserServiceFacade addLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public UserServiceFacade addPolicyName(String policyName) {
		this.policyName = policyName;
		return this;
	}
	
	public UserServiceFacade addIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
		return this;
	}

	public UserServiceFacade addStatus(String status) {
		this.status = status;
		return this;
	}

	public UserServiceFacade addOrgId(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}

	@Override
	public UserServiceFacade addGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	@Override
	public UserServiceFacade addRoleId(String roleId) {
		this.roleId = roleId;
		return this;
	}

	@Override
	public UserServiceFacade addObject(SysUsers sysUsers) {
		this.sysUsers = sysUsers;
		return this;
	}
	
	/** {@inheritDoc} */
	 
	@Override
	public UserServiceFacade addAvailable(String available) {
		this.available = available;
		return this;
	}

	@Override
	public SysQueryService<?> queryUser() throws Exception {
		SysQueryService<?> queryPageService = null;
		if (StringUtils.isEmpty(policyName)) {// 设置默认查询用户本身
			policyName = POLICY_USER;
		}
		if (POLICY_USER.equals(policyName)) {
			// 用户角色关联表
			UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class);
			// 用户资源关联表
			UserResourceFacade userResourceFacade = context.getBean(UserResourceFacade.class);
			// 用户和用户组关联表
			UGroupUserFacade uGroupUserFacade = context.getBean(UGroupUserFacade.class);
			
			// 根据用户id查询用户
			// 添加用户时通过登录名验证是否存在改登录名
			logger.info("查询单条用户信息!!!");
			queryPageService = context.getBean(QueryUser.class)
					.addUserId(userId).addFields(fields).addIsLeaf(isLeaf)
					.addUGroupUserFacade(uGroupUserFacade)
					.addUserResourceFacade(userResourceFacade)
					.addUserRoleFacade(userRoleFacade);

			logger.info("开始执行!!!");
			queryPageService.execute();
		}
		else if (POLICY_RESOURCE.equals(policyName)) {
			logger.info("根据用户id查询关联的资源集合!!!");
			queryPageService = context.getBean(ResourceServiceFacade.class)
					.addUserId(userId).addIsLeaf(isLeaf).addAvailable(available)
					.queryResource(null, null, DataType.List, fields);
			
		}
		else if (POLICY_ROLE.equals(policyName)) {
			logger.info("根据用户id查询关联的角色集合!!!");
			queryPageService = context.getBean(RoleServiceFacade.class)
					.addUserId(userId)
					.queryRole(null, null, DataType.List, fields);
		} 
		else if(POLICY_GROUP.equals(policyName)) {
			logger.info("根据用户id查询关联的用户组集合!!!");
			queryPageService = context.getBean(UserGroupFacade.class)
					.addUserId(userId)
					.queryUserGroup(null, null, DataType.List, fields);
			
		}
		else {
			logger.error("policyName参数输入有误!");
			throw new ChannelException("policyName参数输入有误!");
		}
		return queryPageService;
	}

	@Override
	public SysQueryService<?> queryUser(Integer pageNum, Integer pageSize, DataType dataType, String[] fields) throws Exception {
		SysQueryService<?> queryPageService = null;
		if (dataType == null) {// 设置默认分页查询
			dataType = DataType.Page;
		}
		if (dataType == DataType.Page) {
			if(pageNum == null || pageSize == null) {// 设置分页默认查询的页数
				pageNum  = 1;
				pageSize = 10;
			}
			// 用户角色关联表
			UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class);
			// 用户资源关联表
			UserResourceFacade userResourceFacade = context.getBean(UserResourceFacade.class);
			// 用户和用户组关联表
			UGroupUserFacade uGroupUserFacade = context.getBean(UGroupUserFacade.class);
			
			logger.info("分页查询用户信息!!!");
			queryPageService = context.getBean(QueryPageUsers.class)
					.addQueryParams(pageNum, pageSize, fields)
					.addLoginName(loginNameLike).addUserName(userName)
					.addStatus(status).addOrgId(organizationId)
					.addUserResourceFacade(userResourceFacade)
					.addUserRoleFacade(userRoleFacade)
					.addUGroupUserFacade(uGroupUserFacade);

		} else if (dataType == DataType.List) {
			logger.info("根据用户组id查询用户集合!!!");
			queryPageService = context.getBean(QueryListUsers.class)
					.addFields(fields)
					.addGroupId(groupId)
					.addRoleId(roleId)
					.addLoginName(loginName);

		}else {
			logger.error("dataType参数输入有误!");
			throw new ChannelException("dataType参数输入有误!");
		}
		logger.info("开始执行!!!");
		queryPageService.execute();
		return queryPageService;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveUser() throws Exception {
		BaseService sysMoveService = null;
		if(StringUtils.isEmpty(policyName)) {//	默认修改用户本身
			policyName = POLICY_USER;
		}
		if (POLICY_USER.equals(policyName)) {
			// 用户角色关联表
			UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class);
			List<String> roleIds = sysUsers.getRoleIds() == null ? null : sysUsers.getRoleIds();
			userRoleFacade.setObject(AbsUserRoleService.USER_ID, userId, roleIds);

			// 用户资源关联表
			UserResourceFacade userResourceFacade = context.getBean(UserResourceFacade.class);
			List<String> resourceIds = sysUsers.getResourceIds() == null ? null : sysUsers.getResourceIds();
			userResourceFacade.setObject(AbsUserResourceService.USER_ID, userId, resourceIds);
			
			// 用户和用户组关联表
			UGroupUserFacade uGroupUserFacade = context.getBean(UGroupUserFacade.class);
			List<String> groupIds = sysUsers.getGroupIds() == null ? null : sysUsers.getGroupIds();
			uGroupUserFacade.setObject(AbsUGroupUserService.USER_ID, userId, groupIds);
					
			logger.info("修改用户信息!!!");
			sysMoveService = context.getBean(MoveUser.class)
					.addUserId(userId).addUser(sysUsers)
					.addUGroupUserFacade(uGroupUserFacade)
					.addUserResourceFacade(userResourceFacade)
					.addUserRoleFacade(userRoleFacade);
			
			logger.info("开始执行修改用户操作!!!");
			sysMoveService.execute();
		} else if (POLICY_USERPwd.equals(policyName)) {
			logger.info("重置用户登录密码!!!");
			sysMoveService = context.getBean(MoveUserPsw.class).addUserId(userId);
			
			logger.info("开始执行重置用户登录密码操作!!!");
			sysMoveService.execute();
		} else {
			logger.error("policyName参数输入有误!");
			throw new ChannelException("policyName参数输入有误!");
		}
		
		return sysMoveService;
		
//		else if (POLICY_RESOURCE.equals(policyName)) {
//			// 更新用户关联的资源信息
//			logger.info("重置用户登录密码!!!");
//			sysMoveService = context.getBean(UserResourceFacade.class)
//					.setObject(AbsUserResourceService.USER_ID, userId, ids)// 实例化用户和资源关联表，并传参
//					.moveUserResource();// 执行修改操作
//
//		} else if (POLICY_ROLE.equals(policyName)) {
//			// 更新用户关联的角色信息
//			sysMoveService = context.getBean(UserRoleFacade.class)
//					.setObject(AbsUserResourceService.USER_ID, userId, ids)// 实例化用户和角色关联表，并传参
//					.moveRoleUser();// 执行修改操作
//			
//		}
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveUser(List<StateInstance> status) throws Exception {
		logger.info("批量修改用户状态!!!");
		MoveUserState saveUser = context.getBean(MoveUserState.class).addObject(status);
		
		logger.info("开始执行批量修改用户状态操作!!!");
		saveUser.execute();
		return saveUser;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysSaveService<?> saveUser() throws Exception {
		// 用户与角色关联表
		UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class);
		// 用户与资源关联表
		UserResourceFacade userResourceFacade =	context.getBean(UserResourceFacade.class);
		// 用户与用户组关联表
		UGroupUserFacade uGroupUserFacade = context.getBean(UGroupUserFacade.class);
		
		logger.info("添加用户信息以及关联信息!!!");
		SysSaveService<?> saveUser = context.getBean(SaveUser.class)
				.addUser(sysUsers)
				.addUserRoleFacade(userRoleFacade)
				.addUserResourceFacade(userResourceFacade)
				.addUGroupUserFacade(uGroupUserFacade);
		
		logger.info("执行添加用户操作!!");
		saveUser.execute();
		return saveUser;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService removeUser() throws Exception {
    	// 删除用户和资源关联的资源信息
		UserResourceFacade userResourceFacade =	context.getBean(UserResourceFacade.class)
				.addQueryId(AbsUserResourceService.USER_ID, userId);

		// 删除用户和角色关联的资源信息
		UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class)
				.addQueryId(AbsUserRoleService.USER_ID, userId);
		
		// 删除用户和用户组关联的资源信息
		UGroupUserFacade uGroupUserFacade = context.getBean(UGroupUserFacade.class)
				.addQueryId(AbsUGroupUserService.USER_ID, userId);

		logger.info("根据用户id删除用户以及关联表信息!!!");
		RemoveUser removeUser = context.getBean(RemoveUser.class)
				.addUserId(userId)
				.addUserRoleFacade(userRoleFacade)
				.addUserResourceFacade(userResourceFacade)
				.addUGroupUserFacade(uGroupUserFacade);
		
		logger.info("开始执行删除操作!!!");
		removeUser.execute();
		return removeUser;
	}

	

}
