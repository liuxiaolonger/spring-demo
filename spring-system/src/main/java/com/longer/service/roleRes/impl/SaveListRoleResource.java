package com.longer.service.roleRes.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.SysSaveService;
import com.longer.dao.model.SysRoleResource;
import com.longer.service.roleRes.AbsRoleResourceService;
import com.longer.service.roleRes.vo.RoleResource;

/**
 * 
 * 批量添加角色资源关联表
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("saveListRoleResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveListRoleResource extends AbsRoleResourceService implements SysSaveService<List<RoleResource>>
{
    
    private List<SysRoleResource> lists = new ArrayList<>();
    
    protected SaveListRoleResource setObject(List<SysRoleResource> lists)
    {
        this.lists = lists;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        this.lists.forEach(sysRoleResource -> {
            sysRoleResourceMapper.insert(sysRoleResource);
        });
    }
    
    public List<RoleResource> resultObj()
        throws Exception
    {
        List<RoleResource> list = new ArrayList<>();
        for (SysRoleResource sysRoleResource : lists)
        {
            RoleResource reResource = transferObjectFields(sysRoleResource, RoleResource.class, null);
            list.add(reResource);
        }
     
        return list;
    }
    
}
