package com.longer.service.organization.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.UUIDUtil;
import com.longer.base.SysSaveService;
import com.longer.dao.model.SysOrganization;
import com.longer.service.organization.AbsOrganizationService;
import com.longer.service.organization.vo.Organization;


/**
 * 
 * .组织机构保存
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("saveOrganization")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveOrganization extends AbsOrganizationService implements SysSaveService<Organization>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 保存的Vo对象
     */
	private Organization organization;
    
	protected SaveOrganization setObject(Organization organization) {
		this.organization=organization;
		return this;
	}
    
    @Override
    public void execute() throws Exception
    {
        logger.info("进入保存的方法...");
    	organization.setOrganizationId(UUIDUtil.getUUID());
    	SysOrganization model=transferObjectFields(organization, SysOrganization.class, defaultFiedlds);
    	
		int result= sysOrganizationMapper.insert(model);
		logger.info("影响结果条数 {}",result);
		if (result==0)
        {
            throw new ChannelException("数据库保存异常!");
        }
        
    }

	@Override
	public Organization resultObj() throws Exception {
	    
		return organization;
	}
    
}
