package com.etoc.service.sysparm;

import com.etoc.base.BaseService;
import com.etoc.base.SysQueryService;
import com.etoc.base.SysSaveService;
import com.etoc.constant.DataType;
import com.etoc.service.sysparm.vo.SystemInfoResource;

/**
 * 
 * 系统参数接口定义
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SystemService
{
    
    /**
     * 增加主键查询条件
     * <功能详细描述>
     * @param systemId
     * @return
     * @see [类、类#方法、类#成员]
     */
    SystemService addQueryId(String systemId);
    
    /**
     * 增加系统参数名称查询条件
     * <功能详细描述>
     * @param systemName
     * @return
     * @see [类、类#方法、类#成员]
     */
    SystemService addQuerySystemName(String systemName);
    
    /**
     * 增加系统参数键查询条件
     * <功能详细描述>
     * @param systemKey
     * @return
     * @see [类、类#方法、类#成员]
     */
    SystemService addQuerySystemKey(String systemKey);
    
    /**
     * 增加系统参数值查询条件
     * <功能详细描述>
     * @param systemValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    SystemService addQuerySystemValue(String systemValue);
    
    /**
     * 设置保存、修改的对象
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    SystemService setObject(com.etoc.service.sysparm.vo.SystemInfo system);
    
    /**
     * 查询系统参数信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<SystemInfoResource> querySystem(String[] fields)
        throws Exception;
    
    /**
     * 查询系统参数集合信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> querySystem(Integer pageNum, Integer pageSize, DataType dataType, String[] fields)
        throws Exception;
    
    /**
     * 增加系统参数信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysSaveService<?> saveSystem()
        throws Exception;
    
    /**
     * 修改系统参数信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    BaseService moveSystem()
        throws Exception;
}
