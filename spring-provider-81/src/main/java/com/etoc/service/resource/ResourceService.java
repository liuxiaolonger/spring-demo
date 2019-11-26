package com.etoc.service.resource;

import java.util.List;

import com.etoc.base.BaseService;
import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.service.resource.impl.SaveResource;
import com.etoc.service.resource.vo.Resource;
import com.etoc.service.resource.vo.SysResourceResource;
import com.etoc.vo.StateInstance;

/**
 * 
 * 资源service
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ResourceService
{
    ResourceService addName(String name);
    
    ResourceService addType(String type);
    
    ResourceService addSystemType(Integer systemType);
    
    ResourceService addAvailable(String available);
    
    ResourceService addParentCode(String parentCode);
    
    ResourceService addQueryId(String resourceId);
    
    ResourceService addRoleId(String roleId);
    
    ResourceService addUserId(String userId);
    
    ResourceService addIsLeaf(Integer isLeaf);
    
    /**
     * 设置保存、修改的对象 <功能详细描述>
     * 
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    ResourceService setObject(Resource resource);
    
    /**
     * 查询资源信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<SysResourceResource> queryResource(String[] fields)
        throws Exception;
    
    /**
     * 查询资源信息 <功能详细描述>
     * 
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryResource(Integer pageNum, Integer pageSize, DataType dataType, String[] fields)
        throws Exception;
    
    /**
     * 保存资源信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SaveResource saveResource()
        throws Exception;
    
    /**
     * 修改资源信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveResource()
        throws Exception;
    
    /**
     * 修改资源信息状态 <功能详细描述>
     * 
     * @param obj
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveResource(List<StateInstance> obj)
        throws Exception;
}
