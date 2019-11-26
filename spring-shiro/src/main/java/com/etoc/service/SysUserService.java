package com.etoc.service;

import java.util.List;

import com.etoc.model.SysUser;
import com.etoc.model.SysUserResource;
import com.etoc.model.SysUserRole;
import com.etoc.vo.Checkbox;




/**
 * 用户管理接口
 * 
 * @author longlong
 * @version [版本号, 2018年8月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface SysUserService {
    
	// 根据用户ID查询用户信息
	SysUser selectByFullQuery(String userId);

	// 判断是否存在该用户名
	boolean selectByQuery(String loginName);

	// 查询所有的用户名与ID
	List<Checkbox> selectUserNamesByIds();
	
	//根据登录账号获取用户信息
	SysUser getByLoginName(String loginName);
	
	void modifyUser(SysUser user);
	
	//根据用户id查询角色ids
	List<SysUserRole> getRoleIdsByUserId(String userId);
	
	//根据用户ID查询该用户的资源ids
	List<SysUserResource> getResourceIdsByUserId(String userId);
	
}
