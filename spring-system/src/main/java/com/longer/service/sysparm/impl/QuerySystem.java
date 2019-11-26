package com.longer.service.sysparm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.StringUtil;
import com.longer.base.SysQueryService;
import com.longer.dao.model.SystemInfo;
import com.longer.service.sysparm.AbsSystemService;
import com.longer.service.sysparm.vo.SystemInfoResource;

/**
 * 
 * 单查询系统参数
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

@Service("querySystem")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QuerySystem extends AbsSystemService implements SysQueryService<SystemInfoResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /*
     * 主键
     */
    private String systemId;
    /*
     * 查询到的结果
     */
    private SystemInfo systemInfo;
    
    protected QuerySystem setQueryParams(String[] fields, String systemId)
    {
        this.defaultFiedlds = fields;
        this.systemId = systemId;
        return this;
    }
    
    @Override
    public SystemInfoResource resultObj()
        throws Exception
    {
        logger.info("开始查询");
        if (systemInfo == null)
        {
            return null;
        }
        if (StringUtil.isEmpty(systemInfo.getSystemId()))
        {
            throw new ChannelException("查询主键不能为空!!!");
        }
        SystemInfoResource resource =
            transferObjectFields(systemInfo, SystemInfoResource.class, defaultFiedlds);
        logger.info("数据库查询的结果为:{]", resource);
        
        return resource;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        if (StringUtil.isEmpty(systemId))
        {
            logger.error("查询参数异常");
            throw new ChannelException("参数异常!!!");
        }
        
        systemInfo = systemInfoMapper.selectBySystemId(systemId);
        
    }
    
}
