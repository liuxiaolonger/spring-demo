package com.etoc.service.sysparm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryPageService;
import com.etoc.exception.ChannelException;
import com.etoc.model.SystemInfo;
import com.etoc.service.sysparm.AbsSystemService;
import com.etoc.service.sysparm.vo.SystemInfoResource;
import com.etoc.util.PageInfo;
import com.etoc.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * 分页查询系统参数
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryPageSystem")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageSystem extends AbsSystemService implements SysQueryPageService<SystemInfoResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 页码
     */
    private Integer pageNum;
    
    /*
     * 每页大小
     */
    private Integer pageSize;
    
    /*
     * 参数名称
     */
    private String systemName;
    
    /*
     * 参数键
     */
    private String systemKey;
    
    /*
     * 查询到的结果集
     */
    private List<SystemInfo> list;
    
    protected QueryPageSystem setQueryParams(String systemName, String systemKey)
    {
        this.systemName = systemName;
        this.systemKey = systemKey;
        return this;
    }
    
    protected QueryPageSystem setPageInfo(Integer pageNum, Integer pageSize, String[] fields)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        if (fields != null)
            super.defaultFiedlds = fields;
        return this;
    }
    
    @Override
    public PageInfo<SystemInfoResource> resultObj()
        throws Exception
    {
/*        if (CollectionUtils.isEmpty(list))
        {
            return null;
        }*/
        if (pageNum == 0 || pageSize == 0)
        {
            throw new ChannelException("分页查询的pageNum,pageSize字段都不为空!!!");
        }
        //分页和资源对象转换
        Page<SystemInfoResource> lists = new Page<>(pageNum, pageSize);
        lists.setTotal(((Page<SystemInfo>)list).getTotal());
        lists.setPages(((Page<SystemInfo>)list).getPages());
        for (SystemInfo dto : list)
        {
            SystemInfoResource resource = transferObjectFields(dto, SystemInfoResource.class, defaultFiedlds);
            lists.add(resource);
        }
        
        PageInfo<SystemInfoResource> page = new PageInfo<>(lists,SystemInfoResource.class);
        return page;
        
    }
    
    @Override
    public void execute()
        throws Exception
    {
        logger.info("开始系统参数分页查询...");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(systemName))
        {
            map.put("systemName", systemName);
        }
        if (StringUtil.isNotEmpty(systemKey))
        {
            map.put("systemKey", systemKey);
        }
        PageHelper.startPage(pageNum, pageSize);
        list = systemInfoMapper.selectSystem(map);
    }
    
}
