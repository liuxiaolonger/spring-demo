package com.longer.service.organization.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.util.PageInfo;
import com.etoc.util.SortUtill;
import com.etoc.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.longer.base.impl.SysQueryPageService;
import com.longer.dao.model.SysOrganization;
import com.longer.service.organization.AbsOrganizationService;
import com.longer.service.organization.vo.SysOrganizationResource;

/**
 * 
 * .组织机构分页查询
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryPageOrganization")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageOrganization extends AbsOrganizationService
    implements SysQueryPageService<SysOrganizationResource>
{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /*
     * 当前页
     */
    private Integer pageNum;
    /*
     * 每页大小
     */
    private Integer pageSize;
    /*
     * 排序参数
     */
    private String[] sort;
    /*
     * 组织机构名称
     */
    private String name;
    /*
     * 组织机构编码
     */
    private String code;
    
    List<SysOrganization> sysOrganizations;
    
    protected QueryPageOrganization setPageInfo(Integer pageNum, Integer pageSize, String[] fields, String[] sort)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        if (fields != null)
            super.defaultFiedlds = fields;
        if (sort != null)
            this.sort = sort;
        return this;
    }
    
    protected QueryPageOrganization setQueryParams(String name, String code)
    {
        if (StringUtil.isNotEmpty(name))
            this.name = name;
        if (StringUtil.isNotEmpty(code))
            this.code = code;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        logger.info("数据库组织机构分页查询...");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(name))
        {
            map.put("name", name);
        }
        if (StringUtil.isNotEmpty(code))
        {
            map.put("code", code);
        }
        if (sort != null)
        {
            map.put("sort", SortUtill.getSorts(sort));
        }
        PageHelper.startPage(this.pageNum, this.pageSize);
        sysOrganizations = sysOrganizationMapper.paging(map);
        //查询子机构(如果有的话)
        if (StringUtil.isNotEmpty(code))
        {
            map.clear();//清空条件
            map.put("parentCode", code);
            List<SysOrganization> items = sysOrganizationMapper.paging(map);
            sysOrganizations.addAll(items);
        }
        logger.info("分页数据库机构查询的结果为: {}",sysOrganizations);
        //List<SysUserGroup> group = sysOrganizationMapper.serlectUserGroup(map);
    }
    
    @Override
    public PageInfo<SysOrganizationResource> resultObj()
        throws Exception
    {
        //分页和资源对象转换
        Page<SysOrganizationResource> lists = new Page<>(pageNum, pageSize);
        lists.setTotal(((Page<SysOrganization>)sysOrganizations).getTotal());
        lists.setPages(((Page<SysOrganization>)sysOrganizations).getPages());
        for (SysOrganization organization : sysOrganizations)
        {
            SysOrganizationResource resource = transferObjectFields(organization,
                SysOrganizationResource.class,
                defaultFiedlds);
            lists.add(resource);
        }
        
        PageInfo<SysOrganizationResource> urlInfoPage = new PageInfo<>(lists,SysOrganizationResource.class);
        return urlInfoPage;
        
    }
    
}
