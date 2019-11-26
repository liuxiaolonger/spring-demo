package com.etoc.service.role.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryListService;
import com.etoc.base.impl.SysQueryPageService;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysRole;
import com.etoc.service.role.AbsRoleService;
import com.etoc.service.role.vo.SysRoleResource;
import com.etoc.service.roleRes.impl.RoleResourceFacade;
import com.etoc.service.roleRes.vo.RoleResource;
import com.etoc.service.userRole.impl.UserRoleFacade;
import com.etoc.service.userRole.vo.UserRole;
import com.etoc.util.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * 角色分页
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryPageRoles")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageRoles extends AbsRoleService implements SysQueryPageService<SysRoleResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 当前页码
     */
    private Integer pageNum;
    
    /*
     *每页显示的条数 
     */
    private Integer pageSize;
    
    /*
     * 需要返回的字段
     */
    private String[] fields;
    
    /*
     * 角色名
     */
    private String roleName;
    
    /*
     * 角色状态
     */
    private String available;
    
    /*
     * 查询的结果集
     */
    private List<SysRole> sysRoles;
    
    /*
     *角色用户关联的facade 
     */
    private UserRoleFacade userRoleFacade;
    
    /*
     *角色资源关联的facade 
     */
    private RoleResourceFacade roleResourceFacade;
    
    /*
     * 角色关联的资源map,key为roleid
     */
    private Map<String, List<String>> resourceMap = new HashMap<>();
    
    /*
     * 角色关联的用户map,key为roleid
     */
    private Map<String, List<String>> userMap = new HashMap<>();
    
    protected QueryPageRoles addAvailable(RoleResourceFacade roleResourceFacade)
    {
        this.roleResourceFacade = roleResourceFacade;
        return this;
    }
    
    protected QueryPageRoles addRoleResourceFacade(RoleResourceFacade roleResourceFacade)
    {
        this.roleResourceFacade = roleResourceFacade;
        return this;
    }
    
    protected QueryPageRoles addUserRoleFacade(UserRoleFacade userRoleFacade)
    {
        this.userRoleFacade = userRoleFacade;
        return this;
    }
    
    protected QueryPageRoles setQueryName(String roleName)
    {
        this.roleName = roleName;
        return this;
    }
    
    protected QueryPageRoles setQueryParams(Integer pageNum, Integer pageSize, String[] fields)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.fields = fields == null ? defaultFields : fields;
        
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(roleName))
        {
            map.put(MODEL_ROLENAME, roleName);
        }
        if (StringUtils.isNotEmpty(available))
        {
            map.put(MODEL_AVAILABLE, available);
        }
        PageHelper.startPage(pageNum, pageSize);
        sysRoles = sysRoleMapper.paging(map);
        sysRoles.forEach(role -> {
            try
            {
                String roleId = role.getId();
                logger.info("角色id:" + roleId);
                // 查询用户关联的用户ids
                List<String> userList = new ArrayList<>();
                SysQueryListService<UserRole> userRoleServie =
                    userRoleFacade.addQueryId(ROLE_ID, roleId).queryRoleUser();
                List<UserRole> userRoles = userRoleServie.resultObj();
                userRoles.forEach(userRole -> {
                    userList.add(userRole.getUserId());
                });
                userMap.put(roleId, userList);
                
                // 查询用户关联的角色ids
                List<String> resourceList = new ArrayList<>();
                SysQueryListService<RoleResource> roleResourceService =
                    roleResourceFacade.addQueryId(ROLE_ID, roleId).queryRoleResource();
                List<RoleResource> roleResources = roleResourceService.resultObj();
                roleResources.forEach(roleResource -> {
                    resourceList.add(roleResource.getResourceId());
                });
                resourceMap.put(roleId, resourceList);
            }
            catch (Exception e)
            {
                throw new ChannelException("查询用角色联表信息失败!", e);
            }
        });
    }
    
    @Override
    public PageInfo<SysRoleResource> resultObj()
        throws Exception
    {
        Page<SysRoleResource> lists = new Page<>(pageNum, pageSize);
        
        lists.setTotal(((Page<SysRole>)sysRoles).getTotal());
        for (SysRole role : sysRoles)
        {
            SysRoleResource reRole = transferObjectFields(role, SysRoleResource.class, fields);
            reRole.setResourceIds(resourceMap.get(reRole.getRoleId()));
            reRole.setUserIds(userMap.get(reRole.getRoleId()));
            lists.add(reRole);
        }
        
        PageInfo<SysRoleResource> resourceInfoPage = new PageInfo<>(lists, SysRoleResource.class);
        return resourceInfoPage;
        
    }
    
}
