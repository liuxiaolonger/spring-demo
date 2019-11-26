package com.longer.service.resource.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysResource;
import com.longer.service.resource.AbsResourceService;
import com.longer.service.resource.vo.SysResourceResource;

/**
 * 
 * 查询所有资源
 * 输入参数：fields需要返回的参数
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryListResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListResource extends AbsResourceService implements SysQueryListService<SysResourceResource>
{
    /*
     * 查询结果
     */
    private List<SysResource> sysResources;
    /*
     * 是否是叶子节点
     */
    private Integer isLeaf;
    /*
     * 需要返回的字段
     */
    private String[] fields;
    
    /*
     * 角色id
     */
    private String roleId;
    
    /*
     * 用户
     */
    private String userId;
    
    /*
     * 状态
     */
    private String available;
    
    /*
     * 平台类型
     */
    private Integer systemType;//平台类型
    
    protected QueryListResource addSystemType(Integer systemType)
    {
        this.systemType = systemType;
        return this;
    }
    
    protected QueryListResource addAvailable(String available)
    {
        this.available = available;
        return this;
    }
    
    protected QueryListResource addIsLeaf(Integer isLeaf)
    {
        this.isLeaf = isLeaf;
        return this;
    }
    
    protected QueryListResource addRoleId(String roleId)
    {
        this.roleId = roleId;
        return this;
    }
    
    protected QueryListResource addUserId(String userId)
    {
        this.userId = userId;
        return this;
    }
    
    protected QueryListResource addQuery(String[] fields)
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
            map.put(MODEL_ROLEID, roleId);
        }
        if (StringUtils.isNotEmpty(available))
        {
            map.put(MODEL_AVAILABLE, available);
        }
        if (systemType != null)
        {
            map.put(MODEL_SYSTEMTYPE, systemType);
        }else {
            map.put(MODEL_SYSTEMTYPE, 1);
        }
        if(isLeaf != null) {
        	map.put(MODEL_ISLEAF, isLeaf);
        }
        sysResources = resourceMapper.selectSysResources(map);
    }
    
    @Override
    public List<SysResourceResource> resultObj()
        throws Exception
    {
        List<SysResourceResource> relist = new ArrayList<>();
        
        //转成vo资源
        for (SysResource resource : sysResources)
        {
            SysResourceResource relResource =
                transferObjectFields(resource, SysResourceResource.class, fields);
            relist.add(relResource);
        }
        return relist;
    }
    
}
