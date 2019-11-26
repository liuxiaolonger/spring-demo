package com.etoc.service;

import java.util.List;

import com.etoc.model.SysRole;
import com.etoc.model.SysRoleResource;
import com.etoc.model.SysUserRole;




/**
 * 角色信息管理接口
 * 
 * @author longlong
 * @version [版本号, 2018年8月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface SysRoleService {
	// 根据角色id进行查询角色信息
	SysRole selectByPrimaryKey(String roleId);
	
	//根据角色ID查询资源ids
	List<SysRoleResource> getResourcesIdsById(String id);////00

	//根据角色id查询用户ids
	List<SysUserRole> getUsersIdsById(String id);

	// 查询全部角色信息
	List<SysRole> getSysRoles();
	
	// 通过用户ID查询角色信息
	List<SysRole> listRolesByUserId(String userId);

	//根据角色名称验证是否有该角色名
	SysRole selectByRoleName(String roleName);

	// 修改时验证角色名是否重复
	SysRole selectByNameId(String roleName, String id);

}
