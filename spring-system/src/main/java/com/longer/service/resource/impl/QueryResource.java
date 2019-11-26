package com.longer.service.resource.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.SysQueryService;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysResource;
import com.longer.service.resource.AbsResourceService;
import com.longer.service.resource.vo.SysResourceResource;
import com.longer.service.roleRes.impl.RoleResourceFacade;
import com.longer.service.roleRes.vo.RoleResource;

/**
 * 
 * 查询资源信息
 * 输入参数：资源ID、用户名称
 * 输出参数：资源模型DTO(Resource)
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryResource extends AbsResourceService implements SysQueryService<SysResourceResource>
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 资源id
     */
    private String resourceId;
    
    /*
     * 新增保存的入参对象
     */
    private SysResource sysResource;
    
    /*
     * 需要返回的对象
     */
    private String[] fields;
    
    /*
     * 关联表facade
     */
    private RoleResourceFacade roleResourceFacade;
    
    /*
     * 关联表查询结果
     */
    private List<RoleResource> lists;
    
    protected QueryResource addRoleResourceFacade(RoleResourceFacade roleResourceFacade)
    {
        this.roleResourceFacade = roleResourceFacade;
        return this;
    }
    
    protected QueryResource addQueryParam(String resourceId, String[] fields)
    {
        this.resourceId = resourceId;
        this.fields = fields == null ? defaultFields : fields;
        return this;
    }
    
    /**
     * 执行查询动作
     */
    @Override
    public void execute()
        throws Exception
    {
        sysResource = resourceMapper.selectByPrimaryKey(resourceId);
        // 查询角色对应的资源信息
        SysQueryListService<RoleResource> queryRoleResourceList = roleResourceFacade.queryRoleResource();
        lists = queryRoleResourceList.resultObj();
    }
    
    /**
     * 转换为响应对象
     */
    @Override
    public SysResourceResource resultObj()
        throws Exception
    {
        if (null != sysResource)
        {
            SysResourceResource reResource = transferObjectFields(sysResource, SysResourceResource.class, fields);
            List<String> roleIds = new ArrayList<>();
            lists.forEach(roleResource -> {
                roleIds.add(roleResource.getRoleId());
            });
            reResource.setRoleIds(roleIds);
            return reResource;
        }
        else
        {
            logger.info("未查询到结果!");
            return null;
        }
    }
}
