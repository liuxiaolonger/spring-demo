package com.longer.service.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.longer.base.impl.SysQueryListService;
import com.longer.base.impl.SysQueryPageService;
import com.longer.dao.model.SysUser;

import com.longer.service.guser.AbsUGroupUserService;
import com.longer.service.guser.impl.UGroupUserFacade;
import com.longer.service.guser.vo.UserGroupUser;
import com.longer.service.user.AbsUserService;
import com.longer.service.user.vo.SysUserResource;
import com.longer.service.userRes.AbsUserResourceService;
import com.longer.service.userRes.impl.UserResourceFacade;
import com.longer.service.userRes.vo.UserResource;
import com.longer.service.userRole.AbsUserRoleService;
import com.longer.service.userRole.impl.UserRoleFacade;
import com.longer.service.userRole.vo.UserRole;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageUsers extends AbsUserService implements SysQueryPageService<SysUserResource> {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Integer pageNum;
	private Integer pageSize;
	private String[] fields;
	private String loginName;
	private String userName;
	private String status;
	private String organizationId;

	private UserRoleFacade userRoleFacade;

	private UserResourceFacade userResourceFacade;
	
	private UGroupUserFacade uGroupUserFacade;

	private Map<String, List<String>> ResourceMap = new HashMap<>();

	private Map<String, List<String>> roleMap = new HashMap<>();
	
	private Map<String, List<String>> groupMap = new HashMap<>();

	private List<SysUser> users = new ArrayList<>();

	protected QueryPageUsers addQueryParams(Integer pageNum, Integer pageSize, String[] fields) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.fields = fields;
		return this;
	}

	protected QueryPageUsers addUserRoleFacade(UserRoleFacade userRoleFacade) {
		this.userRoleFacade = userRoleFacade;
		return this;
	}

	protected QueryPageUsers addUserResourceFacade(UserResourceFacade userResourceFacade) {
		this.userResourceFacade = userResourceFacade;
		return this;
	}
	
	protected QueryPageUsers addUGroupUserFacade(UGroupUserFacade uGroupUserFacade) {
		this.uGroupUserFacade = uGroupUserFacade;
		return this;
	}

	protected QueryPageUsers addLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	protected QueryPageUsers addUserName(String userName) {
		this.userName = userName;
		return this;
	}

	protected QueryPageUsers addStatus(String status) {
		this.status = status;
		return this;
	}

	protected QueryPageUsers addOrgId(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(loginName)) {
			map.put(MODEL_LOGINNAME, loginName);
		}
		if (StringUtils.isNotEmpty(userName)) {
			map.put(MODEL_USERNAME, userName);
		}
		if (StringUtils.isNotEmpty(organizationId)) {
			map.put(MODEL_ORGANIZATIONID, organizationId);
		}
		if (StringUtils.isNotEmpty(status)) {
			map.put(MODEL_STATUS, status);
		}

		PageHelper.startPage(pageNum, pageSize);
		users = userMapper.selectByLikeQuery(map);
		logger.info("数据库查询的用户集合users:" + users);
		users.forEach(user -> {
			String userId = user.getId();
			logger.info("用户id:" + userId);
			try {
				// 查询用户关联的资源ids
				List<String> resourceList = new ArrayList<>();
				userResourceFacade.addQueryId(AbsUserResourceService.USER_ID, userId);
				SysQueryListService<UserResource> usersResources = userResourceFacade.queryUserResource();
				List<UserResource> sysUserResources = usersResources.resultObj();
				sysUserResources.forEach(UResource -> {
					resourceList.add(UResource.getResourceId());
				});
				ResourceMap.put(userId, resourceList);
				
				// 查询用户关联的角色ids
				List<String> roleList = new ArrayList<>();
				userRoleFacade.addQueryId(AbsUserRoleService.USER_ID, userId);
				SysQueryListService<UserRole> userRoles = userRoleFacade.queryRoleUser();
				List<UserRole> URoles =  userRoles.resultObj();
				URoles.forEach(URole -> {
					roleList.add(URole.getRoleId());
				});
				roleMap.put(userId, roleList);
				
				// 查询用户关联的用户组信息
				List<String> groupList = new ArrayList<>();
				uGroupUserFacade.addQueryId(AbsUGroupUserService.USER_ID, userId);
				SysQueryListService<UserGroupUser> userGroupUser = uGroupUserFacade.queryUGroupUser();
				List<UserGroupUser> UGroupUsers = userGroupUser.resultObj();
				UGroupUsers.forEach(UGroup -> {
					groupList.add(UGroup.getGroupId());
				});
				groupMap.put(userId, groupList);
			} catch (Exception e) {
				logger.error("查询用户关联表信息失败!",e);
				throw new ChannelException("查询用户关联表信息失败!", e);
			}
		});
		logger.info("分页查询数据库结果users:" + users);
	}


	@Override
	public PageInfo<SysUserResource> resultObj() throws Exception {
		Page<SysUserResource> lists = new Page<>(this.pageNum, this.pageSize);
		this.users.forEach(user -> {
			try {
				SysUserResource us = transferObjectFields(user, SysUserResource.class, fields);
				us.setResourceIds(ResourceMap.get(user.getId()));
				us.setRoleIds(roleMap.get(user.getId()));
				us.setGroupIds(groupMap.get(user.getId()));
				lists.add(us);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败e:" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}

		});
		PageInfo<SysUserResource> userPages = new PageInfo<>(lists,SysUserResource.class);
		userPages.setTotal(((Page<SysUser>) this.users).getTotal());

		return userPages;
	}

}
