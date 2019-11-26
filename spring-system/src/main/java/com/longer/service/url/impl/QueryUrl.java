package com.longer.service.url.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.SysQueryService;
import com.longer.dao.model.SysUrlFilter;
import com.longer.service.url.AbsUrlService;
import com.longer.service.url.vo.SysUrlFilterResource;

/**
 * 
 * 根据id查询url信息
 * <功能详细描述>
 * 
 * @author  liuxiaolong 
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryUrl")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryUrl extends AbsUrlService implements SysQueryService<SysUrlFilterResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * url主键id
     */
    private String urlId;
    
    /*
     * 查询数据库接收对象
     */
    private SysUrlFilter url;
    
    /*
     * 需要的字段
     */
    private String[] fields;
    
    
    protected QueryUrl addQueryParames(String urlId, String[] fields)
    {
        this.fields = fields == null ? defaultFields : fields;
        this.urlId = urlId;
        return this;
    }
    
    /**
     * 根据id查询url
     */
    @Override
    public void execute()
        throws Exception
    {
        url = sysUrlFilterMapper.selectBySysUrlId(urlId);
        
    }
    
    /**
     * 返回查询的结果
     */
    @Override
    public SysUrlFilterResource resultObj()
        throws Exception
    {
        if (null != url)
        {
            SysUrlFilterResource reUrl =
               transferObjectFields(url, SysUrlFilterResource.class, fields);
          return reUrl;
        }
        else
        {
            logger.info("未查询到结果！");
            return null;
        }
        
    }
    
}
