package com.etoc.service.user.impl;

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

import com.etoc.base.SysQueryService;
import com.etoc.base.impl.SysQueryListService;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysUser;
import com.etoc.service.guser.AbsUGroupUserService;
import com.etoc.service.guser.impl.UGroupUserFacade;
import com.etoc.service.guser.vo.UserGroupUser;
import com.etoc.service.user.AbsUserService;
import com.etoc.service.user.vo.SysUserResource;
import com.etoc.service.userRes.AbsUserResourceService;
import com.etoc.service.userRes.impl.UserResourceFacade;
import com.etoc.service.userRes.vo.UserResource;
import com.etoc.service.userRole.AbsUserRoleService;
import com.etoc.service.userRole.impl.UserRoleFacade;
import com.etoc.service.userRole.vo.UserRole;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryUser extends AbsUserService implements SysQueryService<SysUserResource> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userId;

	private Integer isLeaf;

	private String[] fields;

	private SysUser sysUser;

	private UserRoleFacade userRoleFacade;

	private UserResourceFacade userResourceFacade;

	private UGroupUserFacade uGroupUserFacade;

	private Map<String, List<String>> ResourceMap = new HashMap<>();

	private Map<String, List<String>> roleMap = new HashMap<>();

	private Map<String, List<String>> groupMap = new HashMap<>();

	protected QueryUser addUserRoleFacade(UserRoleFacade userRoleFacade) {
		this.userRoleFacade = userRoleFacade;
		return this;
	}

	protected QueryUser addUserResourceFacade(UserResourceFacade userResourceFacade) {
		this.userResourceFacade = userResourceFacade;
		return this;
	}

	protected QueryUser addUGroupUserFacade(UGroupUserFacade uGroupUserFacade) {
		this.uGroupUserFacade = uGroupUserFacade;
		return this;
	}

	protected QueryUser addFields(String[] fields) {
		this.fields = fields;
		return this;
	}

	protected QueryUser addIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
		return this;
	}

	protected QueryUser addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)) {
			map.put(MODEL_ID, userId);
		}

		sysUser = userMapper.selectByQuery(map);
		if (sysUser != null) {
			// 查询用户关联的资源ids
			List<String> resourceList = new ArrayList<>();
			userResourceFacade.addQueryId(AbsUserResourceService.USER_ID, userId).addIsLeaf(isLeaf);
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
			List<UserRole> URoles = userRoles.resultObj();
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

			logger.info("查询结果sysUser：" + sysUser);
		}
	}

	@Override
	public SysUserResource resultObj() throws Exception {
		SysUserResource sysUsers = new SysUserResource();
		try {
			sysUsers = transferObjectFields(sysUser, SysUserResource.class, fields);
			if (sysUsers != null) {
				sysUsers.setResourceIds(ResourceMap.get(userId));
				sysUsers.setRoleIds(roleMap.get(userId));
				sysUsers.setGroupIds(groupMap.get(userId));
			}
		} catch (Exception e) {
			logger.error("数据库对象转化为VO对象失败！" + e);
			throw new ChannelException("数据库对象转化为VO对象失败！", e);
		}
		return sysUsers;
	}

}
