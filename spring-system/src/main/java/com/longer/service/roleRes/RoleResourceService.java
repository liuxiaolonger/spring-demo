package com.longer.service.roleRes;

import java.util.List;

import com.longer.base.BaseService;
import com.longer.base.impl.SysQueryListService;
import com.longer.service.roleRes.vo.RoleResource;

public interface RoleResourceService
{
	RoleResourceService addIsLeaf(Integer isLeaf);  
	  
    RoleResourceService addQueryId(String type, String id);
    
    RoleResourceService setObject(String type, String id, List<String> ids);
    
    SysQueryListService<RoleResource> queryRoleResource() throws Exception;
    
    /**
     * 保存角色资源关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService saveRoleResource() throws Exception;
    
    /**
     * 修改角色资源关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveRoleResource() throws Exception;
    
    /**
     * 删除角色资源关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService removeRoleResource() throws Exception;
}
