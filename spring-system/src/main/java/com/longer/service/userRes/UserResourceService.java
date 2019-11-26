package com.longer.service.userRes;

import java.util.List;

import com.longer.base.BaseService;
import com.longer.base.impl.SysQueryListService;
import com.longer.service.userRes.vo.UserResource;

public interface UserResourceService
{
	// 添加资源过滤条件
	UserResourceService addIsLeaf(Integer isLeaf);
	// 添加参数信息
    UserResourceService addQueryId(String type, String id);
    // 添加参数信息
    UserResourceService setObject(String type, String id, List<String> ids);
    
    /**
	 * 查询用户和资源关联表信息 
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
    SysQueryListService<UserResource> queryUserResource() throws Exception;
    
    /**
     * 保存用户资源关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService saveUserResource() throws Exception;
    
    /**
     * 修改用户资源关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveUserResource() throws Exception;
    
    /**
     * 删除用户资源关联信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService removeUserResource() throws Exception;
}
