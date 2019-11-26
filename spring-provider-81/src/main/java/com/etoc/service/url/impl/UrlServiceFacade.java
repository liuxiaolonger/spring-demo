package com.etoc.service.url.impl;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.base.BaseService;
import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.etoc.service.url.AbsUrlService;
import com.etoc.service.url.UrlService;
import com.etoc.service.url.vo.SysUrlFilterResource;
import com.etoc.service.url.vo.Url;

/**
 * 
 * url信息调度器
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("urlServiceFacade")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UrlServiceFacade extends AbsUrlService implements UrlService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 路径名
     */
    private String urlName;
    
    /*
     * 路径id
     */
    private String urlId;
    
    /*
     * 修改添加时的一个url入参对象
     */
    private Url url;
    
    @Override
    public UrlServiceFacade addQueryId(String urlId)
    {
        this.urlId = urlId;
        return this;
    }
    
    @Override
    public UrlServiceFacade addQueryName(String urlName)
    {
        this.urlName = urlName;
        return this;
    }
    
    @Override
    public UrlServiceFacade setObject(Url url)
    {
        this.url = url;
        return this;
    }
    
    /*
     * 根据条件查询url信息
     */
    @Override
    public SysQueryService<SysUrlFilterResource> queryUrl(String[] fields)
        throws Exception
    {
        SysQueryService<SysUrlFilterResource> queryPageService = context.getBean(QueryUrl.class).addQueryParames(urlId, fields);
        // 执行器
        queryPageService.execute();
        return queryPageService;
    }
    
    /*
     * 分页查询url信息
     */
    @Override
    public SysQueryService<?> queryUrl(Integer pageNum, Integer pageSize, @NotNull DataType dataType, String[] fields)
        throws Exception
    {
        SysQueryService<?> queryPageService = null;
        //分页查询,dataType为空的时候设置默认值为分页
        if (dataType == null || DataType.Page.equals(dataType))
        {
            logger.info("执行分页查询" + dataType);
            queryPageService =
                context.getBean(QueryPageUrl.class).addQueryName(urlName).setQueryParams(pageNum, pageSize, fields);
        }
        else
        {
            logger.error("只支持全分页查询查询！");
            throw new ChannelException("dataType参数错误");
        }
        // 执行器
        queryPageService.execute();
        return queryPageService;
    }
    
    /*
     * 新增url信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaveUrl saveUrl()
        throws Exception
    {
        SaveUrl service = context.getBean(SaveUrl.class).addSysUrlFilter(url);
        // 执行器
        service.execute();
        return service;
    }
    
    /*
     * 修改url信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService moveUrl()
        throws Exception
    {
        
        BaseService service = context.getBean(MoveUrl.class).addSysUrlFilter(url).addUrlId(urlId);
        // 执行器
        service.execute();
        return service;
    }
    
    /*
     * 删除url信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService removeUrl()
        throws Exception
    {
        BaseService service = context.getBean(RemoveUrl.class).addSysUrlFilter(urlId);
        // 执行器
        service.execute();
        return service;
    }
    
}
