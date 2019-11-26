package com.etoc.service.role.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryListService;
import com.etoc.model.SysRole;
import com.etoc.model.SysRoleResource;
import com.etoc.service.role.AbsRoleService;

/**
 * 
 * 查询所有角色，或角色相关联的所有资源
 * 输入参数：fields需要返回的参数
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryListRole")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListRole extends AbsRoleService implements SysQueryListService<SysRoleResource>
{
    /*
     * SysRole查询的结果集
     */
    private List<SysRole> sysRoles;
    
    /*
     * 用户id
     */
    private String userId;
    
    /*
     * 角色id
     */
    private String roleId;
    
    /*
     * 角色名
     */
    private String roleName;
    
    /*
     * 需要返回的字段
     */
    private String[] fields;
    
    /*
     * 角色状态
     */
    private String available;
    
    protected QueryListRole addAvailable(String available)
    {
        this.available = available;
        return this;
    }
    
    protected QueryListRole addRoleId(String roleId)
    {
        this.roleId = roleId;
        return this;
    }
    
    protected QueryListRole addRoleName(String roleName)
    {
        this.roleName = roleName;
        return this;
    }
    
    protected QueryListRole addUserId(String userId)
    {
        this.userId = userId;
        return this;
    }
    
    protected QueryListRole addQuery(String[] fields)
    {
        this.fields = fields == null ? defaultFields : fields;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(userId))
        {
            map.put(MODEL_USERID, userId);
        }
        if (StringUtils.isNotEmpty(roleId))
        {
            map.put(MODEL_ID, roleId);
        }
        if (StringUtils.isNotEmpty(roleName))
        {
            map.put(MODEL_ROLENAME, roleName);
        }
        if (StringUtils.isNotEmpty(available))
        {
            map.put(MODEL_AVAILABLE, available);
        }
        sysRoles = sysRoleMapper.selectSysRoles(map);
    }
    
    @Override
    public List<SysRoleResource> resultObj()
        throws Exception
    {
        List<SysRoleResource> relist = new ArrayList<>();
        for (SysRole role : sysRoles)
        {
            SysRoleResource roleResource =
               transferObjectFields(role, SysRoleResource.class, fields);
            relist.add(roleResource);
        }
        return relist;
        
    }
    
}
