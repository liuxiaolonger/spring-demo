package com.longer.service.url.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.util.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.longer.base.impl.SysQueryPageService;
import com.longer.dao.model.SysUrlFilter;
import com.longer.service.url.AbsUrlService;
import com.longer.service.url.vo.SysUrlFilterResource;

/**
 * 
 * 分页查询url
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryPageUrl")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageUrl extends AbsUrlService implements SysQueryPageService<SysUrlFilterResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
    * 当前页数
    */
    private Integer pageNum;
    
    /*
     * 每页显示的条数
     */
    private Integer pageSize;
    
    /*
     * 需要返回的字段
     */
    private String[] fields;
    
    /*
     * 路径名
     */
    private String urlName;
    
    private List<SysUrlFilter> urls;
    
    protected QueryPageUrl addQueryName(String urlName)
    {
        this.urlName = urlName;
        return this;
    }
    
    protected QueryPageUrl setQueryParams(Integer pageNum, Integer pageSize)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        return this;
    }
    
    protected QueryPageUrl setQueryParams(Integer pageNum, Integer pageSize, String[] fields)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.fields = fields == null ? defaultFields : fields;
        return this;
    }
    
    /**
     * 分页查询
     */
    @Override
    public void execute()
        throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(urlName))
        {
            map.put(MODEL_NAME, urlName);
        }
        PageHelper.startPage(pageNum, pageSize);
        urls = sysUrlFilterMapper.selectSysUrl(map);
        logger.info("分页数据库url查询的结果为: {}", urls);
    }
    
    /**
     * 返回查询结果
     */
    @Override
    public PageInfo<SysUrlFilterResource> resultObj()
        throws Exception
    {
        Page<SysUrlFilterResource> lists = new Page<>(pageNum, pageSize);
        if (CollectionUtils.isNotEmpty(urls))
        {
            lists.setTotal(((Page<SysUrlFilter>)urls).getTotal());
            lists.setPages(((Page<SysUrlFilter>)urls).getPages());
            for (SysUrlFilter url : urls)
            {
                SysUrlFilterResource urlResource =
                    transferObjectFields(url, SysUrlFilterResource.class, fields);
                lists.add(urlResource);
            }
        }
        PageInfo<SysUrlFilterResource> urlInfoPage = new PageInfo<>(lists,SysUrlFilterResource.class);
        return urlInfoPage;
    }
    
}
