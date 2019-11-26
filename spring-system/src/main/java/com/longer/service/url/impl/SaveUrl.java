package com.longer.service.url.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.UUIDUtil;
import com.longer.base.SysSaveService;
import com.longer.dao.model.SysUrlFilter;
import com.longer.service.url.AbsUrlService;
import com.longer.service.url.vo.Url;

/**
 * 
 * 添加url信息
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("saveUrl")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveUrl extends AbsUrlService implements SysSaveService<Url>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 新增时的入参
     */
    private Url url;
    
    protected SaveUrl addSysUrlFilter(Url url)
    {
        this.url = url;
        return this;
    }
    
    /**
     *添加url
     */
    @Override
    public void execute()
        throws Exception
    {
        // 创建资源的唯一标识
        url.setUrlFilterId(UUIDUtil.getUUID());
        SysUrlFilter reUrlFilter =transferObjectFields(url, SysUrlFilter.class, null);
        Integer count = sysUrlFilterMapper.insertSysUrl(reUrlFilter);
        logger.info("新增url信息的条数为{}条", count);
        if (count == 0)
        {
            logger.info("保存url未成功！");
            throw new ChannelException("保存url未成功！");
        }
        
    }
    
    /**
     * 返回添加的url
     */
    @Override
    public Url resultObj()
        throws Exception
    {
        return url;
    }
    
}
