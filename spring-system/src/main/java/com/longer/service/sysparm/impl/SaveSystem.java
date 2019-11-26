package com.longer.service.sysparm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.UUIDUtil;
import com.longer.base.SysSaveService;
import com.longer.service.sysparm.AbsSystemService;
import com.longer.service.sysparm.vo.SystemInfo;

/**
 * 
 * 保存系统参数
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("saveSystem")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveSystem extends AbsSystemService implements SysSaveService<SystemInfo>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /*
     * 待保存的对象
     */
    private SystemInfo systemInfo;
    
    protected SaveSystem setObject(SystemInfo systemInfo)
    {
        this.systemInfo = systemInfo;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        logger.info("开始新增数据");
        if (systemInfo == null)
        {
            logger.error("要新增的系统参数为空");
            throw new ChannelException("要新增的系统参数为空");
        }
        
        systemInfo.setSystemId(UUIDUtil.getUUID());
        com.longer.dao.model.SystemInfo sys =
            transferObjectFields(systemInfo,
            		com.longer.dao.model.SystemInfo.class,
                null);
        
        int result = systemInfoMapper.insertSelective(sys);
        logger.info("数据库操作影响条数: {}", result);
        if (result == 0)
        {
            logger.error("数据库操作失败!!!");
            throw new ChannelException("数据库操作失败!!!");
        }
        
    }
    
    @Override
    public SystemInfo resultObj()
        throws Exception
    {
        return systemInfo;
    }
    
}
