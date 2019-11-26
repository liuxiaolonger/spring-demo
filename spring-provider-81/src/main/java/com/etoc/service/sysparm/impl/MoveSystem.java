package com.etoc.service.sysparm.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.exception.ChannelException;
import com.etoc.service.sysparm.AbsSystemService;
import com.etoc.service.sysparm.vo.SystemInfo;
import com.etoc.util.StringUtil;

/**
 * 
 * 修改系统参数
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("moveSystem")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveSystem extends AbsSystemService implements SysMoveService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 待修改的对象
     */
    private SystemInfo systemInfo;
    
    /*
     * 主键
     */
    private String id;
    
    protected MoveSystem setObject(SystemInfo systemInfo)
    {
        this.systemInfo = systemInfo;
        return this;
    }
    
    protected MoveSystem addQueryId(String id)
    {
        this.id = id;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        logger.info("开始修改数据...");
        if (systemInfo == null || StringUtil.isEmpty(systemInfo.getSystemId()))
        {
            throw new ChannelException("要修改的系统参数为空");
        }
        systemInfo.setSystemId(id);
        com.etoc.model.SystemInfo model =
            transferObjectFields(systemInfo, com.etoc.model.SystemInfo.class, null);
        
        int result = systemInfoMapper.updateByPrimaryKey(model);
        logger.info("数据库操作影响条数: {}", result);
        if (result == 0)
        {
            logger.error("数据库操作失败!!!");
            throw new ChannelException("数据库操作失败!!!");
        }
        
    }
    
}
