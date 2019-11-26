package com.longer.service.organization.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.vo.StateInstance;
import com.longer.base.impl.SysMoveListService;
import com.longer.dao.model.SysOrganization;
import com.longer.service.organization.AbsOrganizationService;

/**
 * 
 * 批量修改组织机构的状态
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("moveOrganizationState")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveOrganizationState extends AbsOrganizationService implements SysMoveListService<StateInstance>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 批量修改状态的对象
     */
    List<StateInstance> obj;
    
    @Override
    public MoveOrganizationState addObject(List<StateInstance> obj)
    {
        this.obj = obj;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        if (CollectionUtils.isEmpty(obj))
            return;
        //修改父组织状态时同时修改子组织的状态(如果有子组织的话)
        obj.forEach(state -> {
            SysOrganization zation = sysOrganizationMapper.selectByPrimaryKey(state.getId());
            if (zation == null)
            {
                logger.error("参数错误!!!");
                throw new ChannelException("參數錯誤!!!");
            }
            
            zation.setAvailable(state.getState().toString());
            sysOrganizationMapper.available(zation);
            
            if (StringUtils.isNotEmpty(zation.getCode()))
            {
                String parentCodes = zation.getParentCodes()  + zation.getCode()+"/";
                List<SysOrganization> list = sysOrganizationMapper.getByFatherIdOrganization(parentCodes);
                list.forEach(item -> {
                    SysOrganization data2 = new SysOrganization();
                    data2.setId(item.getId());
                    data2.setAvailable(state.getState().toString());
                    sysOrganizationMapper.available(data2);
                    
                });
                
            }
            
        });
        
    }
    
}
