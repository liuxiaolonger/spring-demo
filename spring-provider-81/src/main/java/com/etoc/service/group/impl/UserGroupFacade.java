package com.etoc.service.group.impl;

import java.util.List;

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
import com.etoc.service.group.AbsUserGroupService;
import com.etoc.service.group.UserGroupService;
import com.etoc.service.group.vo.UserGroups;
import com.etoc.service.guser.AbsUGroupUserService;
import com.etoc.service.guser.UGroupUserService;
import com.etoc.service.guser.impl.UGroupUserFacade;
import com.etoc.service.user.impl.UserServiceFacade;
import com.etoc.util.StringUtil;
import com.etoc.vo.StateInstance;

/**
 * 
 * 用户组调度器
 * @author  longlong
 * @version  [版本号, 2019年1月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserGroupFacade extends AbsUserGroupService implements UserGroupService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userGroupId;// 用户组id

	private String userGroupName;// 用户组名称

	private UserGroups userGroups;// 用户组返显页面的vo对象资源

	private String policyName;// 判断返回结果是用户组集合还是用户集合

	private String[] fields;// 用户组返显页面的字段
	
	private String userId;// 用户主键id

	/** {@inheritDoc} */

	@Override
	public UserGroupFacade addFields(String[] fields) {
		this.fields = fields;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public UserGroupFacade addUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public UserGroupFacade addUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public UserGroupFacade addPolicyName(String policyName) {
		this.policyName = policyName;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public UserGroupFacade setUserGroups(UserGroups userGroups) {
		this.userGroups = userGroups;
		return this;
	}
	
	/** {@inheritDoc} */
	 
	@Override
	public UserGroupService addUserId(String userId) {
		this.userId = userId;
		return this;
	}
	

	/*
	 * 根据id获取用户组查询对象
	 */
	@Override
	public SysQueryService<?> queryUserGroup() throws Exception {
		SysQueryService<?> queryPageService = null;
		if(StringUtil.isEmpty(policyName))
			policyName = POLICY_GROUP;
		if (POLICY_USER.equals(policyName)) {// 返回关联的用户集合
			logger.info("根据用户组id查询用户信息集合!!!");
			queryPageService = context.getBean(UserServiceFacade.class)
					.addGroupId(userGroupId)
					.queryUser(null, null, DataType.List, fields);

		} else if (POLICY_GROUP.equals(policyName)) { // 返回用户组信息
			// 用户组合用户关联表
			UGroupUserService uGroupService = context.getBean(UGroupUserFacade.class);
			uGroupService.addQueryId(AbsUGroupUserService.GROUP_ID, userGroupId);

			logger.info("查询用户组集合!!!");
			queryPageService = context.getBean(QueryUserGroup.class)
					.addQueryId(userGroupId).addFields(fields)
					.setUGroupUserService(uGroupService);

			queryPageService.execute();
		} else {
			logger.error("policyName参数错误！");
			throw new ChannelException("policyName参数错误");
		}
		return queryPageService;
	}

	/*
	 * 获取用户组查询对象
	 */
	@Override
	public SysQueryService<?> queryUserGroup(Integer pageNum, Integer pageSize, DataType dataType, String[] fields) throws Exception {
		SysQueryService<?> queryPageService = null;
		if (dataType == null) {// 当dataType返回类型为空时，默认为Page分页查询
			dataType = DataType.Page;
		}

		if (dataType == DataType.Page) {
			if (pageNum == null || pageSize == null) {// 设置分页默认查询的页数
				pageNum = 1;
				pageSize = 10;
			}
			// 用户组合用户关联表对应的查询bean
			UGroupUserService uGroupUser = context.getBean(UGroupUserFacade.class);
			
			logger.info("分页查询用户组信息集合！");
			queryPageService = context.getBean(QueryPageUserGroups.class)
					.addQueryName(userGroupName).setQueryParams(pageNum, pageSize, fields)
					.setUGroupUserService(uGroupUser);

		} else if (dataType == DataType.List) {
			logger.info("查询所有用户组下拉菜单！");
			queryPageService = context.getBean(QueryListUserGroups.class).addUserId(userId);

		}else {
			logger.error("dataType参数错误！");
			throw new ChannelException("dataType参数错误");
		}
		logger.info("执行器开始执行！");
		queryPageService.execute();
		return queryPageService;
	}

	/*
	 * 保存用户组
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysSaveService<?> saveUserGroup() throws Exception {
		// 用户组关联的用户信息
		UGroupUserFacade GUserFacade = context.getBean(UGroupUserFacade.class);

		logger.info("保存用户组信息！");
		SaveUserGroup saveService = context.getBean(SaveUserGroup.class).addObject(userGroups);
		saveService.setUGroupUserService(GUserFacade);

		logger.info("保存用户组信息开始执行！");
		saveService.execute();
		return saveService;
	}

	/*
	 * 修改用户组
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveUserGroup() throws Exception {
		// 用户组和用户关联表
		UGroupUserFacade uGroupUserFacade = context.getBean(UGroupUserFacade.class);
		List<String> userIds = userGroups.getUserIds() == null ? null : userGroups.getUserIds();
		uGroupUserFacade.setObject(AbsUGroupUserService.GROUP_ID, userGroupId, userIds);

		logger.info("用户组修改！");
		MoveUserGroup moveService = context.getBean(MoveUserGroup.class);
		moveService.addUserGroupId(userGroupId);
		moveService.addUserGroup(userGroups);
		moveService.addUGroupUserFacade(uGroupUserFacade);
		
		logger.info("用户组修改开始执行！");
		moveService.execute();
		return moveService;
	}

	/*
	 * 批量变更用户组状态
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveUserGroup(List<StateInstance> stateIns) throws Exception {
		logger.info("批量变更用户组状态！");
		MoveUserGoupsState moveStateService = context.getBean(MoveUserGoupsState.class);
		moveStateService.addObject(stateIns);

		logger.info("批量变更用户组状态开始执行！");
		moveStateService.execute();
		return moveStateService;
	}

	/*
	 * 删除用户组
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService removeUserGroup() throws Exception {
		// 根据用户组关联表
		UGroupUserFacade uGroupUserFacade = context.getBean(UGroupUserFacade.class)
				.addQueryId(AbsUGroupUserService.GROUP_ID, userGroupId);

		logger.info("根据id删除用户组！");
		RemoveUserGroup removeService = context.getBean(RemoveUserGroup.class);
		removeService.addMoveId(userGroupId);
		removeService.addUGroupUserFacade(uGroupUserFacade);

		logger.info("根据id删除用户组开始执行！");
		removeService.execute();
		return removeService;
	}

	

}
