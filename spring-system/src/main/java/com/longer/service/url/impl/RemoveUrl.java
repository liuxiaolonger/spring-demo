package com.longer.service.url.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.longer.base.SysRemoveService;
import com.longer.service.url.AbsUrlService;

/**
 * 
 * 删除url信息
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("removeUrl")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RemoveUrl extends AbsUrlService implements SysRemoveService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * url主键id
     */
    private String urlId;
    
    protected RemoveUrl addSysUrlFilter(String urlId)
    {
        this.urlId = urlId;
        return this;
    }
    
    /**
     * 删除url
     */
    @Override
    public void execute()
        throws Exception
    {
        Integer count = sysUrlFilterMapper.deleteSysUrl(urlId);
        logger.info("删除url的行数为{}", count);
        if (count == 0)
        {
            logger.info("刪除url未成功！");
            throw new ChannelException("刪除url未成功！");
        }
    }
    
}
