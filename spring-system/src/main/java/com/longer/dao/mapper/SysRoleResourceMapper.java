package com.longer.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.longer.dao.model.SysRoleResource;

@Mapper
public interface SysRoleResourceMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role_resource
	 *
	 * @mbg.generated Wed Jun 27 15:31:04 CST 2018
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role_resource
	 *
	 * @mbg.generated Wed Jun 27 15:31:04 CST 2018
	 */
	int insert(SysRoleResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role_resource
	 *
	 * @mbg.generated Wed Jun 27 15:31:04 CST 2018
	 */
	int insertSelective(SysRoleResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role_resource
	 *
	 * @mbg.generated Wed Jun 27 15:31:04 CST 2018
	 */
	SysRoleResource selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role_resource
	 *
	 * @mbg.generated Wed Jun 27 15:31:04 CST 2018
	 */
	int updateByPrimaryKeySelective(SysRoleResource record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table sys_role_resource
	 *
	 * @mbg.generated Wed Jun 27 15:31:04 CST 2018
	 */
	int updateByPrimaryKey(SysRoleResource record);

	// 根据角色ID和资源条件查询关联表
	List<SysRoleResource> selectBySysResourceModel(Map<String, Object> map);

	// 根据角色id进行删除
	int deleteSysResourceModel(Map<String, Object> map);
}