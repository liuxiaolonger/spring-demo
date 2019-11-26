package com.etoc.service.group;

import java.util.List;

import com.etoc.base.BaseService;
import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.service.group.vo.UserGroups;
import com.etoc.vo.StateInstance;

public interface UserGroupService
{
    
    UserGroupService addUserGroupId(String userGroupId);
    
    UserGroupService addUserGroupName(String userGroupName);
    
    UserGroupService addPolicyName(String policyName);
    
    UserGroupService addFields(String[] fields);
    
    UserGroupService addUserId(String userId);
    
    /**
     * 设置保存、修改的对象
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    UserGroupService setUserGroups(UserGroups userGroups);
    
    
    /**
     * 查询用户组
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryUserGroup() throws Exception;
    
    /**
     * 查询用户组
     * <功能详细描述>
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryUserGroup(Integer pageNum, Integer pageSize, DataType dataType, String[] fields) throws Exception;
    
    /**
     * 保存用户组
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService saveUserGroup() throws Exception;
    
    /**
     * 修改用户组
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveUserGroup() throws Exception;
    
    /**
     * 修改用户组
     * <功能详细描述>
     * @param obj
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveUserGroup(List<StateInstance> obj) throws Exception;
    
    /**
     * 删除用户组
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService removeUserGroup() throws Exception;
    
}
