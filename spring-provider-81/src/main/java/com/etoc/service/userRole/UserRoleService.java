package com.etoc.service.userRole;

import java.util.List;

import com.etoc.base.BaseService;
import com.etoc.base.impl.SysQueryListService;
import com.etoc.service.userRole.vo.UserRole;

public interface UserRoleService {
	
	
	UserRoleService addQueryId(String type, String id);

	UserRoleService setObject(String type, String id, List<String> ids);

	/**
	 * 查询用户和角色关联表信息
	 * 
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	SysQueryListService<UserRole> queryRoleUser() throws Exception;

	/**
	 * 保存用户和角色关联表信息
	 * 
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	BaseService saveRoleUser() throws Exception;

	/**
	 * 修改用户和角色关联表信息
	 * 
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	BaseService moveRoleUser() throws Exception;

	/**
	 * 删除用户和角色关联表信息
	 * 
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	BaseService removeRoleUser() throws Exception;
}
