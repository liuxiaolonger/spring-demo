package com.longer.service.sysparm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.longer.base.BaseService;
import com.longer.base.SysQueryService;
import com.longer.base.SysSaveService;
import com.longer.service.sysparm.AbsSystemService;
import com.longer.service.sysparm.SystemService;
import com.longer.service.sysparm.vo.SystemInfoResource;

/**
 * 
 * 系统参数调度器
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("systemServiceFacade")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SystemServiceFacade extends AbsSystemService implements SystemService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 系统参数主键查询条件
     */
    private String systemId;
    
    /*
     * 系统参数名称查询条件
     */
    private String systemName;
    
    /*
     * 系统参数键查询条件
     */
    private String systemKey;
    
    /*
     * 系统参数值查询条件
     */
    private String systemValue;
    
    /*
     * 新增和修改的对象
     */
    private com.longer.service.sysparm.vo.SystemInfo system;
    
    @Override
    public SystemService addQueryId(String systemId)
    {
        this.systemId = systemId;
        return this;
    }
    
    @Override
    public SystemService addQuerySystemName(String systemName)
    {
        this.systemName = systemName;
        return this;
    }
    
    @Override
    public SystemService addQuerySystemKey(String systemKey)
    {
        this.systemKey = systemKey;
        return this;
    }
    
    @Override
    public SystemService addQuerySystemValue(String systemValue)
    {
        this.systemValue = systemValue;
        return this;
    }
    
    @Override
    public SystemService setObject(com.longer.service.sysparm.vo.SystemInfo system)
    {
        this.system = system;
        return this;
    }
    
    @Override
    public SysQueryService<SystemInfoResource> querySystem(String[] fields)
        throws Exception
    {
        SysQueryService<SystemInfoResource> sysQueryService = context.getBean(QuerySystem.class).setQueryParams(fields, systemId);
        // 执行器
        sysQueryService.execute();
        return sysQueryService;
    }
    
    @Override
    public SysQueryService<?> querySystem(Integer pageNum, Integer pageSize, DataType dataType, String[] fields)
        throws Exception
    {
        SysQueryService<?> sysQueryService = null;
        //分页查询
        if (DataType.Page.equals(dataType))
        {
            logger.info("系统参数分页查询," + dataType);
            sysQueryService = context.getBean(QueryPageSystem.class)
                .setPageInfo(pageNum, pageSize, fields)
                .setQueryParams(systemName, systemKey);
        }
        //全查询
        else if (DataType.List.equals(dataType))
        {
            logger.info("系统参数全查询," + dataType);
            sysQueryService = context.getBean(QueryListSystem.class)
                .setQueryParams(systemId, systemName, systemKey, systemValue, defaultFiedlds);
        }
        else
        {
            logger.error("参数错误");
            throw new ChannelException("参数错误");
        }
        
        // 执行器
        sysQueryService.execute();
        return sysQueryService;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysSaveService<?> saveSystem()
        throws Exception
    {
        SysSaveService<?> sysQueryService = context.getBean(SaveSystem.class).setObject(system);
        // 执行器
        sysQueryService.execute();
        return sysQueryService;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService moveSystem()
        throws Exception
    {
        system.setSystemId(systemId);
        BaseService sysQueryService = context.getBean(MoveSystem.class).setObject(system).addQueryId(systemId);
        
        // 执行器
        sysQueryService.execute();
        return sysQueryService;
    }
    
}
