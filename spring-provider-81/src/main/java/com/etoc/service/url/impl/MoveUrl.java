package com.etoc.service.url.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.model.SysUrlFilter;
import com.etoc.service.url.AbsUrlService;
import com.etoc.service.url.vo.Url;

/**
 * 
 * 修改url信息
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("moveUrl")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveUrl extends AbsUrlService implements SysMoveService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private Url url;
    
    private String urlId;
    
    protected MoveUrl addUrlId(String urlId)
    {
        this.urlId = urlId;
        return this;
    }
    
    protected MoveUrl addSysUrlFilter(Url url)
    {
        this.url = url;
        return this;
    }
    
    /**
     * 执行修改
     */
    @Override
    public void execute()
        throws Exception
    {
        url.setUrlFilterId(urlId);
        SysUrlFilter reUrlFilter = transferObjectFields(url, SysUrlFilter.class, null);
        Integer count = sysUrlFilterMapper.updateSysUrl(reUrlFilter);
        logger.info("修改url受影响的行数为{}", count);
        if (count == 0)
        {
            logger.error("修改url未成功！");
        }
        
    }
    
}
