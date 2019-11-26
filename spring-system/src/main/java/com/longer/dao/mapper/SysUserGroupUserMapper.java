package com.longer.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.longer.dao.model.SysUserGroupUser;

@Mapper
public interface SysUserGroupUserMapper {
	/**
	 * 添加 用户组和用户关联表 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int insertUserAndGroup(SysUserGroupUser sysUserGroupInfo);

	/**
	 * 分页查询用户组和用户关联表 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<SysUserGroupUser> serlectUserAndGroup(Map<String, Object> map);

	/**
	 * 根据Id查询信息 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	SysUserGroupUser serlectByUserAndGroupId(@Param("id") String id);

	/**
	 * 根据条件查询关联表信息 <功能详细描述>
	 * 
	 * @param groupId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<SysUserGroupUser> serlectByParms(Map<String, Object> map);

	/**
	 * 保存修改后的用户组和用户关联表信息 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int updateUserAndGroup(SysUserGroupUser sysUserGroupUserInfo);

	/**
	 * 删除关联表信息 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int deleteByPrimaryKey(Map<String, Object> map);

}