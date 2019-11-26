package com.etoc.service.role.impl;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.base.BaseService;
import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.service.resource.impl.ResourceServiceFacade;
import com.etoc.service.role.AbsRoleService;
import com.etoc.service.role.RoleService;
import com.etoc.service.role.vo.Role;
import com.etoc.service.roleRes.impl.RoleResourceFacade;
import com.etoc.service.user.impl.UserServiceFacade;
import com.etoc.service.userRole.impl.UserRoleFacade;
import com.etoc.vo.StateInstance;

/**
 * 
 * 角色调度器
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("roleServiceFacade")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoleServiceFacade extends AbsRoleService implements RoleService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 角色id
     */
    private String roleId;
    
    /*
     * 角色名
     */
    private String roleName;
    
    /*
     * 修改,添加时的入参对象
     */
    private Role role;
    
    private Integer isLeaf;
    
    /*
     *用户主键 
     */
    private String userId;
    
    /*
     * 判断返回结果是角色集合还是用户集合
     */
    private String policyName;
    
    /*
     * 角色关联资源表的Facade
     */
    protected RoleResourceFacade roleResourceFacade;
    
    /*
     * 需要返回的字段
     */
    private String[] fields;
    
    /*
     * 角色状态
     */
    private String available;
    
    @Override
    public RoleService addAvailable(String available)
    {
        this.available = available;
        return this;
    }
    
    /**
     * 设置查询条件-资源ID(主键)
     */
    @Override
    public RoleService addUserId(String userId)
    {
        this.userId = userId;
        return this;
    }
    
    @Override
    public RoleService addIsLeaf(Integer isLeaf)
    {
        this.isLeaf = isLeaf;
        return this;
    }
    
    @Override
    public RoleService setFields(String[] fields)
    {
        this.fields = fields;
        return this;
    }
    
    @Override
    public RoleService setRoleResourceFacade(RoleResourceFacade roleResourceFacade)
    {
        this.roleResourceFacade = roleResourceFacade;
        return this;
    }
    
    @Override
    public RoleService addPolicyName(String policyName)
    {
        this.policyName = policyName;
        return this;
    }
    
    @Override
    public RoleService addQueryId(String roleId)
    {
        this.roleId = roleId;
        return this;
    }
    
    @Override
    public RoleService addQueryName(String roleName)
    {
        this.roleName = roleName;
        return this;
    }
    
    @Override
    public RoleService setObject(Role role)
    {
        this.role = role;
        return this;
    }
    
    @Override
    public SysQueryService<?> queryRole()
        throws Exception
    {
        SysQueryService<?> queryService = null;
        if (POLICY_ROLE.equals(policyName))
        {
            // 根据角色id查询资源信息ids
            RoleResourceFacade roleResourceService =
                context.getBean(RoleResourceFacade.class).addQueryId(ROLE_ID, roleId).addIsLeaf(isLeaf);
            // 根据角色id查询用户信息ids
            UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class).addQueryId(ROLE_ID, roleId);
            // 返回角色
            queryService = context.getBean(QueryRole.class)
                .addQueryId(roleId, fields)
                .addUserRoleFacade(userRoleFacade)
                .addRoleResourceFacade(roleResourceService);
            queryService.execute();
        }
        //角色id查用户集合
        else if (POLICY_USER.equals(policyName))
        {
            // 根据用户组id查询用户信息集合
            queryService =
                context.getBean(UserServiceFacade.class).addRoleId(roleId).queryUser(null, null, DataType.List, fields);
        }
        //角色id查资源集合
        else if (POLICY_RESOURCE.equals(policyName))
        {
            queryService = context.getBean(ResourceServiceFacade.class)
                .addRoleId(roleId)
                .addIsLeaf(isLeaf)
                .queryResource(null, null, DataType.List, fields);
        }
        return queryService;
    }
    
    @Override
    public SysQueryService<?> queryRole(Integer pageNum, Integer pageSize, DataType dataType, String[] fields)
        throws Exception
    {
        // TODO Auto-generated method stub
        SysQueryService<?> queryPageService = null;
        //分页查询返回page
        if (dataType == null || DataType.Page.equals(dataType))
        {
            // 根据角色id查询资源信息ids
            RoleResourceFacade roleResourceService = context.getBean(RoleResourceFacade.class);
            // 根据角色id查询用户信息ids
            UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class);
            logger.info("角色分页查询," + dataType);
            queryPageService = context.getBean(QueryPageRoles.class)
                .setQueryParams(pageNum, pageSize, fields)
                .setQueryName(roleName)
                .addRoleResourceFacade(roleResourceService)
                .addUserRoleFacade(userRoleFacade);
            queryPageService.execute();
        }
        //查询所有返回list
        else if (DataType.List.equals(dataType))
        {
            logger.info("角色条件查询返回值为list,{}", dataType);
            queryPageService = context.getBean(QueryListRole.class)
                .addQuery(fields)
                .addUserId(userId)
                .addRoleId(roleId)
                .addRoleName(roleName)
                .addAvailable(available);
            queryPageService.execute();
        }
        return queryPageService;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaveRole saveRole()
        throws Exception
    {
        // 角色关联的资源信息
        RoleResourceFacade roleResourceFacade = context.getBean(RoleResourceFacade.class);
        
        //角色用户关联表
        UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class);
        SaveRole service = context.getBean(SaveRole.class).addObject(role);
        service.addRoleResourceFacade(roleResourceFacade);
        service.addUserRoleFacade(userRoleFacade);
        // 执行器
        service.execute();
        return service;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService moveRole()
        throws Exception
    {
        
        // 角色资源关联表
        RoleResourceFacade roleResourceFacade = context.getBean(RoleResourceFacade.class);
        List<String> resourceIds = role.getResourceIds() == null ? null : role.getResourceIds();
        roleResourceFacade.setObject(ROLE_ID, roleId, resourceIds);
        
        //角色用户关联表
        UserRoleFacade userRoleFacade = context.getBean(UserRoleFacade.class);
        List<String> userIds = role.getUserIds() == null ? null : role.getUserIds();
        userRoleFacade.setObject(ROLE_ID, roleId, userIds);
        BaseService moveSerrvice = context.getBean(MoveRole.class)
            .addObject(role)
            .addRoleResourceFacade(roleResourceFacade)
            .addUserRoleFacade(userRoleFacade)
            .addRoleId(roleId);
        
        moveSerrvice.execute();
        
        return moveSerrvice;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService moveRole(List<StateInstance> obj)
        throws Exception
    {
        BaseService service = context.getBean(MoveRoleState.class).addObject(obj);
        // 执行器
        service.execute();
        return service;
    }
    
}
