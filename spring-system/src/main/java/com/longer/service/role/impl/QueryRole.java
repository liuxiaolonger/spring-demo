package com.longer.service.role.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.SysQueryService;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysRole;
import com.longer.service.role.AbsRoleService;
import com.longer.service.role.vo.Role;
import com.longer.service.role.vo.SysRoleResource;
import com.longer.service.roleRes.impl.RoleResourceFacade;
import com.longer.service.roleRes.vo.RoleResource;
import com.longer.service.userRole.impl.UserRoleFacade;
import com.longer.service.userRole.vo.UserRole;

/**
 * 
 * 角色相关的查询
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryRole")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryRole extends AbsRoleService implements SysQueryService<SysRoleResource>
{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 资源id
     */
    private String roleId;
    
    /*
     * 新增保存的入参对象
     */
    private SysRole sysRole;
    
    /*
     *角色用户关联的facade 
     */
    private UserRoleFacade userRoleFacade;
    
    /*
     * 查询角色用户关联的结果集
     */
    private List<UserRole> userRoleList;
    
    /*
     *角色资源关联的facade 
     */
    private RoleResourceFacade roleResourceFacade;
    
    /*
     * 查询角色资源关联的结果集
     */
    private List<RoleResource> roleResourceList;
    
    /*
     * 需要返回的字段
     */
    private String[] fields;
    
    protected QueryRole addRoleResourceFacade(RoleResourceFacade roleResourceFacade)
    {
        this.roleResourceFacade = roleResourceFacade;
        return this;
    }
    
    protected QueryRole addUserRoleFacade(UserRoleFacade userRoleFacade)
    {
        this.userRoleFacade = userRoleFacade;
        return this;
    }
    
    protected QueryRole addQueryId(String roleId, String[] fields)
    {
        this.fields = fields;
        this.roleId = roleId;
        return this;
    }
    
    /**
     * 执行查询动作
     */
    @Override
    public void execute()
        throws Exception
    {
        sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (sysRole == null)
        {
            logger.info("查询角色信息失败@！！！");
            return;
        }
        
        // 查询角色对应的用户信息
        SysQueryListService<UserRole> queryUserRoleList = userRoleFacade.queryRoleUser();
        userRoleList = queryUserRoleList.resultObj();
        
        // 查询角色对应的资源信息
        SysQueryListService<RoleResource> queryRoleResourceList = roleResourceFacade.queryRoleResource();
        roleResourceList = queryRoleResourceList.resultObj();
    }
    
    /**
     * 转换为响应对象
     */
    @Override
    public SysRoleResource resultObj()
        throws Exception
    {
        Role role = transferObjectFields(sysRole, Role.class, null);
        List<String> userIds = new ArrayList<>();
        userRoleList.forEach(userRole -> {
            userIds.add(userRole.getUserId());
        });
        List<String> resourceIds = new ArrayList<>();
        roleResourceList.forEach(roleRe -> {
            resourceIds.add(roleRe.getResourceId());
        });
        role.setUserIds(userIds);
        role.setResourceIds(resourceIds);
        SysRoleResource reRole = transferObjectFields(role, SysRoleResource.class, fields);    
        return reRole;
    }
    
}
