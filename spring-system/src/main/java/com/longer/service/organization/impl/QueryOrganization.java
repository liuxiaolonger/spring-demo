package com.longer.service.organization.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.StringUtil;
import com.longer.base.SysQueryService;
import com.longer.dao.model.SysOrganization;
import com.longer.service.organization.AbsOrganizationService;
import com.longer.service.organization.vo.SysOrganizationResource;

/**
 * 
 * .组织机构单查询
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryOrganization")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryOrganization extends AbsOrganizationService implements SysQueryService<SysOrganizationResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 组织机构主键
     */
    private String id;
    
    /*
     * 数据库查询的结果对象
     */
    private SysOrganization sysOrganization;
    
    protected QueryOrganization setQueryParams(String[] fields, String id)
    {
        if (fields != null)
            super.defaultFiedlds = fields;
        if (StringUtil.isNotEmpty(id))
            this.id = id;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        //单个查询
        if (StringUtil.isEmpty(id))
        {
            logger.error("参数异常!!");
            throw new ChannelException("单个组织几个查询的参数异常!!");
        }
        sysOrganization = sysOrganizationMapper.selectByPrimaryKey(id);
        logger.info("数据库查询的结果为: {}", sysOrganization);
        
    }
    
    @Override
    public SysOrganizationResource resultObj()
        throws Exception
    {
        //当查询结果为空时,直接返回null 避免转换异常
        if (sysOrganization == null)
        {
            return null;
        }
        
        SysOrganizationResource result = transferObjectFields(sysOrganization,
            SysOrganizationResource.class,
            defaultFiedlds);
        return result;
    }
    
}
