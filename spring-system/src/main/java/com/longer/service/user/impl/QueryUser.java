package com.longer.service.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.longer.base.SysQueryService;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysUser;
import com.longer.service.guser.AbsUGroupUserService;
import com.longer.service.guser.impl.UGroupUserFacade;
import com.longer.service.guser.vo.UserGroupUser;
import com.longer.service.roleRes.AbsRoleResourceService;
import com.longer.service.roleRes.impl.RoleResourceFacade;
import com.longer.service.roleRes.vo.RoleResource;
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
public class QueryUser extends AbsUserService implements SysQueryService<SysUserResource> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userId;

	private Integer isLeaf;

	private String[] fields;

	private SysUser sysUser;

	private UserRoleFacade userRoleFacade;

	private UserResourceFacade userResourceFacade;

	private UGroupUserFacade uGroupUserFacade;

	private RoleResourceFacade roleResourceFacade;

	private Map<String, Set<String>> ResourceMap = new HashMap<>();

	private Map<String, Set<String>> roleMap = new HashMap<>();

	private Map<String, Set<String>> groupMap = new HashMap<>();

	protected QueryUser addRoleResourceFacade(RoleResourceFacade roleResourceFacade) {
		this.roleResourceFacade = roleResourceFacade;
		return this;
	}

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
//			List<String> resourceList = new ArrayList<>();
			Set<String> resourceList = new HashSet<>();
			userResourceFacade.addQueryId(AbsUserResourceService.USER_ID, userId).addIsLeaf(isLeaf);
			SysQueryListService<UserResource> usersResources = userResourceFacade.queryUserResource();
			List<UserResource> sysUserResources = usersResources.resultObj();
			sysUserResources.forEach(UResource -> {
				resourceList.add(UResource.getResourceId());
			});
			ResourceMap.put(userId, resourceList);

			// 查询用户关联的角色ids
//			List<String> roleList = new ArrayList<>();
			Set<String> roleList = new HashSet<>();
			userRoleFacade.addQueryId(AbsUserRoleService.USER_ID, userId);
			SysQueryListService<UserRole> userRoles = userRoleFacade.queryRoleUser();
			List<UserRole> URoles = userRoles.resultObj();
			URoles.forEach(URole -> {
				roleList.add(URole.getRoleId());
				roleResourceFacade.addQueryId(AbsRoleResourceService.ROLE_ID, URole.getRoleId()).addIsLeaf(isLeaf);
				try {
					SysQueryListService<RoleResource> roleResourceService = roleResourceFacade.queryRoleResource();
					List<RoleResource> roleResource = roleResourceService.resultObj();
					roleResource.forEach(resource -> {
						resourceList.add(resource.getResourceId());
					});
				} catch (Exception e) {
					logger.error("查询角色关联的资源信息失败！1");
					throw new ChannelException("查询角色关联的资源信息失败！1", e);
				}
			});
			roleMap.put(userId, roleList);
			ResourceMap.put(userId, resourceList);

			// 查询用户关联的用户组信息
//			List<String> groupList = new ArrayList<>();
			Set<String> groupList = new HashSet<>();
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
				List<String> resourceIds = new ArrayList<>(ResourceMap.get(userId));
				List<String> roleIds = new ArrayList<>(roleMap.get(userId));
				List<String> groupIds = new ArrayList<>(groupMap.get(userId));
				sysUsers.setResourceIds(resourceIds);
				sysUsers.setRoleIds(roleIds);
				sysUsers.setGroupIds(groupIds);
			}
		} catch (Exception e) {
			logger.error("数据库对象转化为VO对象失败！" + e);
			throw new ChannelException("数据库对象转化为VO对象失败！", e);
		}
		return sysUsers;
	}

}
