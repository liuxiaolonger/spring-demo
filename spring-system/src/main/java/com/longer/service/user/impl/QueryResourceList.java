/*
 * 文 件 名:  QueryResourceList.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2019年6月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.user.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.longer.base.SysQueryService;
import com.longer.base.impl.SysQueryListService;
import com.longer.service.resource.impl.ResourceServiceFacade;
import com.longer.service.resource.vo.SysResourceResource;
import com.longer.service.user.AbsUserService;
import com.longer.service.userRole.AbsUserRoleService;
import com.longer.service.userRole.impl.UserRoleFacade;
import com.longer.service.userRole.vo.UserRole;





/**
 * 查询用户关联的资源集合
 * 
 * @author longlong
 * @version [版本号, 2019年6月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryResourceList extends AbsUserService implements SysQueryListService<SysResourceResource> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userId;// 用户主键id
	private Integer isLeaf;// 是否是叶子节点
	private String available;// 状态

	private String[] fields;

	private UserRoleFacade userRoleFacade;

	private ResourceServiceFacade resourceServiceFacade;
	
	private List<SysResourceResource> ResourceList = new ArrayList<>();// 保存用户关联的资源集合

	protected QueryResourceList addFields(String[] fields) {
		this.fields = fields;
		return this;
	}
	
	protected QueryResourceList addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	protected QueryResourceList addIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
		return this;
	}

	protected QueryResourceList addAvailable(String available) {
		this.available = available;
		return this;
	}

	protected QueryResourceList addUserRoleFacade(UserRoleFacade userRoleFacade) {
		this.userRoleFacade = userRoleFacade;
		return this;
	}

	protected QueryResourceList addResourceServiceFacade(ResourceServiceFacade resourceServiceFacade) {
		this.resourceServiceFacade = resourceServiceFacade;
		return this;
	}

	/** {@inheritDoc} */

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception {
		userRoleFacade.addQueryId(AbsUserRoleService.USER_ID, userId);
		// 查询用户关联的角色ids
		SysQueryListService<UserRole> userRoles = userRoleFacade.queryRoleUser();
		List<UserRole> URoles = userRoles.resultObj();
		URoles.forEach(URole -> {
			String roleId = URole.getRoleId();// 角色主键id
			resourceServiceFacade.addAvailable(available).addIsLeaf(isLeaf).addUserId(null).addRoleId(roleId);
			try {
				// 查询角色关联的资源集合
				SysQueryService<?> resourceService = resourceServiceFacade.queryResource(null, null, DataType.List, fields);
				List<SysResourceResource> setList = (List<SysResourceResource>) resourceService.resultObj();
				ResourceList.addAll(setList);
			} catch (Exception e) {
				logger.error("查询角色关联的资源集合失败！！！");
				throw new ChannelException("查询角色关联的资源集合失败！！！");
			}
		});
		resourceServiceFacade.addAvailable(available).addIsLeaf(isLeaf).addUserId(userId).addRoleId(null);
		// 查询用户关联的资源集合
		SysQueryService<?> resourceService = resourceServiceFacade.queryResource(null, null, DataType.List, fields);
		List<SysResourceResource> setList = (List<SysResourceResource>) resourceService.resultObj();
		ResourceList.addAll(setList);
	}

	/** {@inheritDoc} */

	@Override
	public List<SysResourceResource> resultObj() throws Exception {
		// 通过set集合过滤掉重复的资源
		Set<SysResourceResource> resourceSet = new HashSet<>(this.ResourceList);
		List<SysResourceResource> resourceList = new ArrayList<>(resourceSet);
		return resourceList;
	}

}
