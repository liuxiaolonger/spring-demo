package com.etoc.service.role.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.model.SysRole;
import com.etoc.service.role.AbsRoleService;
import com.etoc.service.role.vo.Role;
import com.etoc.service.roleRes.impl.RoleResourceFacade;
import com.etoc.service.userRole.impl.UserRoleFacade;
import com.etoc.util.StringUtil;

import io.netty.channel.ChannelException;

/**
 * 
 * 修改角色,角色关联的资源
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("moveRole")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveRole extends AbsRoleService implements SysMoveService
{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 角色用户关联表的facade
     */
    private UserRoleFacade userRoleFacade;
    
    /*
     * 角色资源联表的facade
     */
    private RoleResourceFacade roleResourceFacade;
    
    /*
     * 修改的入参对象
     */
    private Role role;
    
    /*
     * role主键id
     */
    private String roleId;
    
    protected MoveRole addRoleId(String roleId)
    {
        this.roleId = roleId;
        return this;
    }
    
    protected MoveRole addUserRoleFacade(UserRoleFacade userRoleFacade)
    {
        this.userRoleFacade = userRoleFacade;
        return this;
    }
    
    protected MoveRole addRoleResourceFacade(RoleResourceFacade roleResourceFacade)
    {
        this.roleResourceFacade = roleResourceFacade;
        return this;
    }
    
    protected MoveRole addObject(Role role)
    {
        this.role = role;
        return this;
    }
    
    /**
     * 修改角色
     */
    @Override
    public void execute()
        throws Exception
    {
        //赋值id
        role.setRoleId(roleId);
        SysRole reRole = transferObjectFields(role, SysRole.class, null);
        logger.info("转化为数据库model对象的结果reRole = " + reRole);
        if (!objectIsEmpty(reRole, MODEL_ID))
        {
            // 修改角色
        	 if(StringUtil.isEmpty(reRole.getAvailable())) {
     			logger.error("要修改角色的状态不能为空！");
     			throw new ChannelException("要修改角色的状态不能为空！");
     		}
     		if(!("available".equals(reRole.getAvailable()))) {
     			logger.error("只能修改状态为激活的角色！");
     			throw new ChannelException("只能修改状态为激活的角色！");
     		}
            Integer count = sysRoleMapper.updateByPrimaryKey(reRole);
            logger.info("修改角色信息的条数为{}条", count);
            if (count == 0)
            {
                logger.error("修改角色未成功！");
            }
        }
        
        // 角色资源关联表
        roleResourceFacade.moveRoleResource();
        
        //角色用户关联表
        userRoleFacade.moveRoleUser();
        
    }
    
}
