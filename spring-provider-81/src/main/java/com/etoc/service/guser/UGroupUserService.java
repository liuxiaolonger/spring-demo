package com.etoc.service.guser;

import java.util.List;

import com.etoc.base.BaseService;
import com.etoc.base.impl.SysQueryListService;
import com.etoc.service.guser.vo.UserGroupUser;

/**
 * 
 * 用户组关联表业务实现
 * <功能详细描述>
 * 
 * @author  chenzhi
 * @version  [版本号, 2018年12月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface UGroupUserService
{
    
    UGroupUserService addQueryId(String type, String id);
    
    UGroupUserService setObject(String type, String id, List<String> ids);
    
    SysQueryListService<UserGroupUser> queryUGroupUser() throws Exception;
    
    /**
     * 保存用户组关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService saveUGroupUser() throws Exception;
    
    /**
     * 修改用户组关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveUGroupUser() throws Exception;
    
    /**
     * 删除用户组关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService removeUGroupUser() throws Exception;
}
