package com.etoc.service.role.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysSaveService;
import com.etoc.constant.CommonConst;
import com.etoc.constant.StateEnum;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysRole;
import com.etoc.service.role.AbsRoleService;
import com.etoc.service.role.vo.Role;
import com.etoc.service.roleRes.impl.RoleResourceFacade;
import com.etoc.service.userRole.impl.UserRoleFacade;
import com.etoc.util.UUIDUtil;

/**
 * 
 * 保存角色,角色关联的资源
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("saveRole")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveRole extends AbsRoleService implements SysSaveService<Role>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 角色资源的facade
     */
    private RoleResourceFacade roleResourceFacade;
    
    /*
     * 新增入参对象
     */
    private Role role;
    
    /*
     * 角色用户关联表的facade
     */
    private UserRoleFacade userRoleFacade;
    
    protected SaveRole addUserRoleFacade(UserRoleFacade userRoleFacade)
    {
        this.userRoleFacade = userRoleFacade;
        return this;
    }
    
    protected SaveRole addObject(Role role)
    {
        this.role = role;
        return this;
    }
    
    protected void addRoleResourceFacade(RoleResourceFacade roleResourceFacade)
    {
        this.roleResourceFacade = roleResourceFacade;
    }
    
    /**
     * 保存角色
     */
    @Override
    public void execute()
        throws Exception
    {
        //设置主键id,默认值
        role.setRoleId(UUIDUtil.getUUID());
        role.setAvailable(StateEnum.available.name());
        role.setSystemType(CommonConst.system.STATUS_ONE);
        SysRole reRole = transferObjectFields(role, SysRole.class, null);
        Integer count = sysRoleMapper.insert(reRole);
        logger.info("保存角色信息的条数为{}条", count);
        if (count == 0)
        {
            logger.info("保存角色未成功！");
            throw new ChannelException("保存角色未成功！");
        }
        // 在角色关联表中添加关联的资源信息
        roleResourceFacade.setObject(ROLE_ID, reRole.getId(), role.getResourceIds());
        
        roleResourceFacade.saveRoleResource();
        // 在角色关联表中添加关联的用户信息
        userRoleFacade.setObject(ROLE_ID, reRole.getId(), role.getUserIds());
        
        userRoleFacade.saveRoleUser();
        
    }
    
    /** {@inheritDoc} */
    
    @Override
    public Role resultObj()
        throws Exception
    {
        
        return role;
    }
    
}
