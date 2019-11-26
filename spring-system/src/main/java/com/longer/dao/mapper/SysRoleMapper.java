package com.longer.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.longer.dao.model.SysRole;

@Mapper
public interface SysRoleMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role
	 *
	 * @mbg.generated Wed Jun 27 11:18:42 CST 2018
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role
	 *
	 * @mbg.generated Wed Jun 27 11:18:42 CST 2018
	 */
	int insert(SysRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role
	 *
	 * @mbg.generated Wed Jun 27 11:18:42 CST 2018
	 */
	int insertSelective(SysRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role
	 *
	 * @mbg.generated Wed Jun 27 11:18:42 CST 2018
	 */
	SysRole selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role
	 *
	 * @mbg.generated Wed Jun 27 11:18:42 CST 2018
	 */
	int updateByPrimaryKeySelective(SysRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role
	 *
	 * @mbg.generated Wed Jun 27 11:18:42 CST 2018
	 */
	int updateByPrimaryKey(SysRole record);

	// 查询所有角色信息
	List<SysRole> selectSysRoles(Map<String, Object> map);

	// 查询角色信息分页
	List<SysRole> paging(Map<String, Object> query);

	// 根据角色id修改角色状态
	int updateAvailable(Map<String, Object> map);

	// 根据userId查询角色信息
	List<SysRole> selectRolesByUserId(String userId);

	// 根据角色名得到角色
	SysRole selectByRoleName(Map<String, Object> map);

	// 根据角色名和id验证编辑时的用户名
	SysRole selectByNameId(Map<String, Object> map);
}