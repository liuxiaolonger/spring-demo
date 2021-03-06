package com.etoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.etoc.model.SysUserResource;


@Mapper
public interface SysUserResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_resource
     *
     * @mbg.generated Wed Jun 27 15:41:31 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_resource
     *
     * @mbg.generated Wed Jun 27 15:41:31 CST 2018
     */
    int insert(SysUserResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_resource
     *
     * @mbg.generated Wed Jun 27 15:41:31 CST 2018
     */
    int insertSelective(SysUserResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_resource
     *
     * @mbg.generated Wed Jun 27 15:41:31 CST 2018
     */
    SysUserResource selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_resource
     *
     * @mbg.generated Wed Jun 27 15:41:31 CST 2018
     */
    int updateByPrimaryKeySelective(SysUserResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_resource
     *
     * @mbg.generated Wed Jun 27 15:41:31 CST 2018
     */
    int updateByPrimaryKey(SysUserResource record);
    
    //根据用户ID查询关联表信息
    List<SysUserResource> selectBySysResourceModel(SysUserResource record);
    
    //根据用户ID删除关联信息
    int deleteByUserId(String userId);
}