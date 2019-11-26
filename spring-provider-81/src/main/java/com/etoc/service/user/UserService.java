package com.etoc.service.user;

import java.util.List;

import com.etoc.base.BaseService;
import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.service.user.impl.UserServiceFacade;
import com.etoc.service.user.vo.SysUsers;
import com.etoc.vo.StateInstance;

public interface UserService 
{

    UserService addUserId(String userId);
    
    UserService addUserName(String userName);

    UserService addGroupId(String groupId);
    
    UserService addRoleId(String roleId);
    
    UserService addObject(SysUsers sysUsers);
    
    UserServiceFacade addFields(String[] fields);
    
    UserService addIsLeaf(Integer isLeaf);
    
    UserService addAvailable(String available);
    /**
     * 查询用户信息-
     * 根据用户ID查询用户资源
     * 一般作用在返回结果只包含一条记录
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryUser() throws Exception;
    
    /**
     * 查询用户信息-
     * 一般作用在返回结果包含多条记录
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryUser(Integer pageNum, Integer pageSize, DataType dataType, String[] fields) throws Exception;
    
    /**
     * 修改用户信息
     * 修改用户关联的资源信息
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveUser() throws Exception;
    
    /**
     * 修改用户信息-冻结、解冻用户
     * 
     * @param obj
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveUser(List<StateInstance> obj) throws Exception;
    
    /**
     * 新增用户
     * 新增用户资源
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService saveUser() throws Exception;
    
    /**
     * 删除用户信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService removeUser() throws Exception;
    
    
    
}
