package com.etoc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.etoc.model.SysUserRole;


@Mapper
public interface SysUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Wed Jun 27 15:13:58 CST 2018
     */
	//删除中间表信息
    int deleteByPrimaryKey(Map<String, Object> map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Wed Jun 27 15:13:58 CST 2018
     */
    int insert(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Wed Jun 27 15:13:58 CST 2018
     */
    int insertSelective(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Wed Jun 27 15:13:58 CST 2018
     */
    SysUserRole selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Wed Jun 27 15:13:58 CST 2018
     */
    int updateByPrimaryKeySelective(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Wed Jun 27 15:13:58 CST 2018
     */
    int updateByPrimaryKey(SysUserRole record);
    
    //根据条件查询用户与角色关联信息
    List<SysUserRole> selectByQuery(Map<String, Object> map);
    
    
}