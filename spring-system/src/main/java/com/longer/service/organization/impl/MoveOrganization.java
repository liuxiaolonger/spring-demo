package com.longer.service.organization.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.StringUtil;
import com.longer.base.SysMoveService;
import com.longer.dao.model.SysOrganization;
import com.longer.service.organization.AbsOrganizationService;
import com.longer.service.organization.vo.Organization;

/**
 * 
 * .修改组织机构
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("moveOrganization")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveOrganization extends AbsOrganizationService implements SysMoveService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 待修改的对象
     */
    private Organization organization;
    /*
     * 主键
     */
    private String id;
    
    protected MoveOrganization setObject(Organization organization)
    {
        this.organization = organization;
        return this;
    }
    
    protected MoveOrganization addQueryId(String id)
    {
        this.id = id;
        return this;
    }
    
    
    
    @Override
    public void execute()
        throws Exception
    {
        logger.info("进入修改执行器");
        //修改组织机构
        if (organization==null || StringUtil.isEmpty(id))
        {
           throw new ChannelException("参数异常!!!");   
        }
        //给对象主键赋值
        organization.setOrganizationId(id);
        SysOrganization model = transferObjectFields(organization, SysOrganization.class, null);
        
        int result = sysOrganizationMapper.updateByPrimaryKeySelective(model);
        logger.info("修改组织机构影响的条数为: {}",result); 
        if (result == 0)
        {
            logger.error("修改组织机构时操作数据库异常!");
            throw new ChannelException("修改组织机构时操作数据库异常!");
        }
        
    }
    
}
