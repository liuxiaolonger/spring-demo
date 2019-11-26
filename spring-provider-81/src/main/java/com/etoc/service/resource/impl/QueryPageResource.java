package com.etoc.service.resource.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryPageService;
import com.etoc.model.SysResource;
import com.etoc.service.resource.AbsResourceService;
import com.etoc.service.resource.vo.SysResourceResource;
import com.etoc.util.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * 分页查询资源
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryPageResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageResource extends AbsResourceService implements SysQueryPageService<SysResourceResource>
{
    /*
     * 当前页码
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
     * 资源名
     */
    private String name;
    
    /*
     * 资源类型
     */
    private String type;//资源类型
    
    /*
     * 平台类型
     */
    private Integer systemType;//平台类型
    
    /*
     * 状态
     */
    private String available;//状态
    
    /*
     * 父编号资源编码
     */
    private String parentCode;//父编号资源编码
    
    /*
     * 查询的结果集
     */
    private List<SysResource> sysResources;
    
    protected QueryPageResource setQueryParams(String name, String type, Integer systemType, String available,
        String parentCode)
    {
        this.name = name;
        this.type = type;
        this.systemType = systemType;
        this.available = available;
        this.parentCode = parentCode;
        return this;
    }
    
    protected QueryPageResource setQueryParams(Integer pageNum, Integer pageSize)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        return this;
    }
    
    protected QueryPageResource setQueryParams(Integer pageNum, Integer pageSize, String[] fields)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.fields = fields == null ? defaultFields : fields;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(name))
        {
            map.put(MODEL_NAME, name);
        }
        if (StringUtils.isNotEmpty(type))
        {
            map.put(MODEL_TYPE, type);
        }
        if (StringUtils.isNotEmpty(parentCode))
        {
            map.put(MODEL_PARENTCODE, parentCode);
        }
        if (systemType != null)
        {
            map.put(MODEL_SYSTEMTYPE, systemType);
        }
        if (available != null)
        {
            map.put(MODEL_AVAILABLE, available);
        }
        PageHelper.startPage(pageNum, pageSize);
        sysResources = resourceMapper.paging(map);
    }
    
    @Override
    public PageInfo<SysResourceResource> resultObj()
        throws Exception
    {
        Page<SysResourceResource> lists = new Page<>(pageNum, pageSize);
        if (CollectionUtils.isNotEmpty(sysResources))
        {
            lists.setTotal(((Page<SysResource>)sysResources).getTotal());
            lists.setPages(((Page<SysResource>)sysResources).getPages());
            for (SysResource resource : sysResources)
            {
                SysResourceResource relResource =
                    transferObjectFields(resource, SysResourceResource.class, fields);
                lists.add(relResource);
            }
        }
        PageInfo<SysResourceResource> resourceInfoPage = new PageInfo<>(lists,SysResourceResource.class);
        
        return resourceInfoPage;
        
    }
    
}
