package com.longer.service.roleRes.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.SysRemoveService;
import com.longer.service.roleRes.AbsRoleResourceService;

/**
 * 
 * 批量删除角色资源关联表 <功能详细描述>
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("removeListRoleResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RemoveListRoleResource extends AbsRoleResourceService implements SysRemoveService
{
    
    private String roleId;
    
    private String resourceId;
    
    protected RemoveListRoleResource addResourceId(String resourceId)
    {
        this.resourceId = resourceId;
        return this;
    }
    
    protected RemoveListRoleResource addRoleId(String roleId)
    {
        this.roleId = roleId;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(resourceId))
        {
            map.put(MODEL_RESOURCEID, resourceId);
        }
        if (StringUtils.isNotEmpty(roleId))
        {
            map.put(MODEL_ROLEID, roleId);
        }
        if (map.size() != 0)
        {
            sysRoleResourceMapper.deleteSysResourceModel(map);
        }
    }
    
}
