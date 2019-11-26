package com.longer.service.role;

import java.util.List;

import com.etoc.constant.DataType;
import com.etoc.vo.StateInstance;
import com.longer.base.BaseService;
import com.longer.base.SysQueryService;
import com.longer.service.role.impl.SaveRole;
import com.longer.service.role.vo.Role;
import com.longer.service.roleRes.impl.RoleResourceFacade;

public interface RoleService
{
    RoleService addUserId(String userId);
    
    RoleService addQueryId(String roleId);
    
    RoleService addQueryName(String roleName);
    
    RoleService addPolicyName(String policyName);
    
    RoleService addAvailable(String available);
    
    RoleService addIsLeaf(Integer isLeaf);
    
    RoleService setFields(String[] fields);
    
    RoleService setRoleResourceFacade(RoleResourceFacade roleResourceFacade);
    
    /**
     * 设置保存、修改的对象
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    RoleService setObject(Role role);
    
    /**
     * 查询角色信息
     * 
     * 根据角色ID查询资源ids
     * 根据id查询用户ids
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryRole()
        throws Exception;
    
    /**
     * 查询角色信息
     * <功能详细描述>
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryRole(Integer pageNum, Integer pageSize, DataType dataType, String[] fields)
        throws Exception;
    
    /**
     * 增加角色
     * 增加角色对应的资源信息
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SaveRole saveRole()
        throws Exception;
    
    /**
     * 修改角色
     * 修改角色对应的资源信息或用户信息
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveRole()
        throws Exception;
    
    /**
     * 修改角色信息状态 <功能详细描述>
     * @param obj
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveRole(List<StateInstance> obj)
        throws Exception;
}
