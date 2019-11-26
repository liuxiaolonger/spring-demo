package com.longer.service.organization.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.SortUtill;
import com.etoc.util.StringUtil;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysOrganization;
import com.longer.service.organization.AbsOrganizationService;
import com.longer.service.organization.vo.SysOrganizationResource;

/**
 * 
 * .组织机构多查询
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryListOrganization")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListOrganization extends AbsOrganizationService
    implements SysQueryListService<SysOrganizationResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 组织机构主键
     */
    private String id;
    
    /*
     * 排序规则
     */
    private String[] sort;
    
    /*
     * 查询的集合
     */
    private List<SysOrganization> sysOrganizations = new ArrayList<>();
    
    /*
     * 组织机构状态
     */
    private String available;
    
    private String code;
    
    protected QueryListOrganization setQueryCode(String code)
    {
        this.code = code;
        return this;
    }
    
    protected QueryListOrganization setQueryParams(String id, String available, String[] fields, String[] sort)
    {
        //如果有个性化字段显示需求则覆盖默认的
        this.defaultFiedlds =fields==null?this.defaultFiedlds:fields;
        
        this.id = id;
        
        this.available = available;
        
        this.sort = sort;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        //全查询状态为正常的机构信息
        if ( StringUtil.isEmpty(id))
        {
            logger.info("全查询数据");
            if (StringUtil.isNotEmpty(available))
                map.put("available", available);
            if (StringUtil.isNotEmpty(code))
                map.put("code", code);
                sysOrganizations = sysOrganizationMapper.selectAll(map);
            //sysOrganizations = sysOrganizationMapper.paging(map);
            logger.info("组织机构全查询的结果为: {}", sysOrganizations);
            return;
        }
        //id不为空则查询子机构,如果没有子机构则返回自己
        if (StringUtil.isNotEmpty(id))
        {
            logger.info("子机构数据查询");
            SysOrganization organization = sysOrganizationMapper.selectByPrimaryKey(id);
            if (organization == null || StringUtils.isEmpty(organization.getParentCodes()))
            {
                logger.info("父机构信息不存在,查询失败!");
                throw new ChannelException("父机构信息不存在！");
            }
            String parentCodes = organization.getParentCodes() + organization.getCode() + "/";
            map.put("parentCodeLike", parentCodes);
            sysOrganizations = sysOrganizationMapper.selectAll(map);
            //sysOrganizations = sysOrganizationMapper.getByFatherIdOrganization(parentCodes);
            // 如果没有子机构就返回本身
//            if (sysOrganizations.size() == 0)  // 不管有没有子机构都返回本身
                sysOrganizations.add(organization);
            
            logger.info("子组织机构全查询的结果为: {}", sysOrganizations);
            return;
        }
        
        //如果sort排序规则只一个,而且排序内容为 -sort 则查询code的最大值
        if (sort == null)
        {
            logger.error("参数不正确,查询失败,param为,id=" + id + ",available=" + available + ",sort=" + sort);
            throw new ChannelException("参数不正确,查询失败！");
        }
        
        if (sort.length == 1 & "-code".equals(sort[0]))
        {
            logger.info("查询code最大值");
            map.put("sort", SortUtill.getSorts(sort));
            map.put("pageSize", 1);//只取第一条数据
            sysOrganizations = sysOrganizationMapper.selectAll(map);
            logger.info("组织机构查询code的最大值为: {}", sysOrganizations);
            return;
        }
        
    }
    
    @Override
    public List<SysOrganizationResource> resultObj()
        throws Exception
    {
        //批量转换对象
        List<SysOrganizationResource> results = new ArrayList<>();
        if (CollectionUtils.isEmpty(sysOrganizations))
            return null;
        
        for (SysOrganization sysOrganization : sysOrganizations)
        {
            SysOrganizationResource result = transferObjectFields(sysOrganization,
                SysOrganizationResource.class,
                defaultFiedlds);
            results.add(result);
        }
        
        return results;
    }
    
}
