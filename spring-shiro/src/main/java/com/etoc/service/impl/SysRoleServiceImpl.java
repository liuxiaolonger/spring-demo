package com.etoc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.mapper.SysRoleMapper;
import com.etoc.mapper.SysRoleResourceMapper;
import com.etoc.mapper.SysUserRoleMapper;
import com.etoc.model.SysRole;
import com.etoc.model.SysRoleResource;
import com.etoc.model.SysUserRole;
import com.etoc.service.SysRoleService;
import com.etoc.util.UUIDUtil;

/**
 * 角色信息管理接口
 * 
 * @author longlong
 * @version [版本号, 2018年8月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysRoleResourceMapper sysRoleResourceMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	/** {@inheritDoc} */

	@Override
	public SysRole selectByPrimaryKey(String roleId) {
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		return sysRole;
	}

	/** {@inheritDoc} */

	@Override
	public List<SysRole> getSysRoles() {
		List<SysRole> sysRoles = sysRoleMapper.selectSysRoles();
		return sysRoles;
	}

	@Override
	public List<SysRole> listRolesByUserId(String userId) {
		List<SysRole> roles = sysRoleMapper.selectRolesByUserId(userId);
		return roles;
	}

	@Override
	public SysRole selectByRoleName(String roleName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", roleName);
		return sysRoleMapper.selectByRoleName(map);
	}

	@Override
	public SysRole selectByNameId(String roleName, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName", roleName);
		map.put("id", id);
		return sysRoleMapper.selectByNameId(map);
	}

	/**
	 * 添加角色对应的资源信息
	 * 
	 * @param roleId
	 * @param resourceIds
	 * @see [类、类#方法、类#成员]
	 */
	public List<SysRoleResource> insertRoleResource(String roleId, String resourceIds) {
		if (StringUtils.isEmpty(roleId)) {
			throw new ChannelException("角色id不能为空！");
		}
		String[] resourceIdArray = StringUtils.isBlank(resourceIds) ? new String[0] : resourceIds.split(",");
		List<SysRoleResource> list = new ArrayList<>();
		for (String resourceId : resourceIdArray) {
			SysRoleResource sysRoleResource = new SysRoleResource();
			String uuid = UUIDUtil.getUUID();
			// 创建返回结果
			sysRoleResource.setId(uuid);// 创建唯一标识
			sysRoleResource.setResourceId(resourceId);
			sysRoleResource.setRoleId(roleId);
			// 更新增加后数据
			list.add(sysRoleResource);
			sysRoleResourceMapper.insert(sysRoleResource);
		}
		return list;
	}

	/** {@inheritDoc} */

	@Override
	public List<SysUserRole> getUsersIdsById(String id) {
		SysUserRole sur = new SysUserRole();
		sur.setRoleId(id);
		List<SysUserRole> userRoles = sysUserRoleMapper.selectByQuery(sur);
		return userRoles;
	}

	/** {@inheritDoc} */

	@Override
	public List<SysRoleResource> getResourcesIdsById(String id) {
		SysRoleResource roleResource = new SysRoleResource();
		roleResource.setRoleId(id);
		List<SysRoleResource> lists = sysRoleResourceMapper.selectBySysResourceModel(roleResource);
		return lists;
	}

	/**
	 * 添加用户和角色关联表资源
	 * 
	 * @param userId
	 * @param roleId
	 * @see [类、类#方法、类#成员]
	 */
	public void add(String rolId, String userIds) {
		if(StringUtils.isEmpty(rolId)) {
			throw new ChannelException("角色id不能为空！");
		}
		String[] userId = StringUtils.isEmpty(userIds) ? new String[0] : userIds.split(",");
		for (int i = 0; i < userId.length; i++) {
			// 创建跟新后的关联信息
			SysUserRole userRole = new SysUserRole();
			userRole.setId(UUIDUtil.getUUID());
			userRole.setRoleId(rolId);
			userRole.setUserId(userId[i]);
			sysUserRoleMapper.insert(userRole);
		}
		
		
	}

}
