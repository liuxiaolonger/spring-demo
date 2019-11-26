package com.etoc.service.roleRes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryListService;
import com.etoc.model.SysRoleResource;
import com.etoc.service.roleRes.AbsRoleResourceService;
import com.etoc.service.roleRes.vo.RoleResource;

/**
 * 
 * 查找角色资源关联表
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryListRoleResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListRoleResource extends AbsRoleResourceService implements SysQueryListService<RoleResource>
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    private String roleId;
    
    private String resourceId;
    
    private Integer isLeaf;
    
    private List<SysRoleResource> sysRoleResources = new ArrayList<>();
    
    protected QueryListRoleResource addResourceId(String resourceId)
    {
        this.resourceId = resourceId;
        return this;
    }
    
    protected QueryListRoleResource addIsLeaf(Integer isLeaf)
    {
        this.isLeaf = isLeaf;
        return this;
    }
    
    protected QueryListRoleResource addRoleId(String roleId)
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
        if(isLeaf != null) {
        	map.put(MODEL_ISLEAF, isLeaf);
        }
        if(map.size()!=0) {
        sysRoleResources = sysRoleResourceMapper.selectBySysResourceModel(map);
        }
        if (CollectionUtils.isEmpty(sysRoleResources))
        {
            logger.error("关联表为空！");
        }
        
    }
    
    /*
     * 返回结果集
     */
    @Override
    public List<RoleResource> resultObj()
        throws Exception
    {
        List<RoleResource> roleResources = new ArrayList<>();
        for (SysRoleResource resource : sysRoleResources)
        {
            RoleResource reRole = transferObjectFields(resource, RoleResource.class, null);
            roleResources.add(reRole);
        }
        return roleResources;
    }
    
}
