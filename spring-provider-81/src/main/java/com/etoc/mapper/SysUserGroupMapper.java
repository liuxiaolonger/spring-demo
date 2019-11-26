/*
 * 文 件 名:  SysUsergroupMapper.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2018年8月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoc.model.SysUserGroup;

/**
 * 用户组管理
 * 
 * @author longlong
 * @version [版本号, 2018年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Mapper
public interface SysUserGroupMapper {

	/**
	 * 添加用户组 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int insertGroup(SysUserGroup sysUserGroup);

	/**
	 * 分页查询用户组 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<SysUserGroup> serlectUserGroup(Map<String, Object> map);

	/**
	 * 查询所有用户组信息
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<SysUserGroup> serlectAllUserGroup(Map<String, Object> map);

	/**
	 * 根据用户组id查询用户组 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	SysUserGroup serlectByGroupId(String sysUserGroupId);

	/**
	 * 保存修改后的用户组信息 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int updateGroup(SysUserGroup sysUserGroup);

	/**
	 * 删除用户组信息 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int deleteGroup(@Param("groupId") String id);

}
