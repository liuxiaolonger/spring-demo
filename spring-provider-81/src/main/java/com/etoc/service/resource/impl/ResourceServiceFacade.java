package com.etoc.service.resource.impl;

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
import com.etoc.service.resource.AbsResourceService;
import com.etoc.service.resource.ResourceService;
import com.etoc.service.resource.vo.Resource;
import com.etoc.service.resource.vo.SysResourceResource;
import com.etoc.service.roleRes.impl.RoleResourceFacade;
import com.etoc.vo.StateInstance;

/**
 * 
 * 资源信息装饰器
 * <功能详细描述>
 * 
 * @author  chenzhi
 * @version  [版本号, 2018年12月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("resourceServiceFacade")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResourceServiceFacade extends AbsResourceService implements ResourceService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 新增修改时入参对象
     */
    private Resource resource;
    
    /*
     * 资源主键id
     */
    private String id;
    
    /*
     *角色id
     */
    private String roleId;
    
    /*
     *角色id
     */
    private String userId;
    
    /*
     *资源名称 
     */
    private String name;
    
    /*
     *资源类型
     */
    private String type;
    
    /*
     *平台类型
     */
    private Integer systemType;
    
    /*
     *状态
     */
    private String available;
    
    /*
     *父编号资源编码
     */
    private String parentCode;
    
    /*
     * 是否是叶子节点
     */
    private Integer isLeaf;
    
    /** {@inheritDoc} */
	 
	@Override
	public ResourceService addType(String type) {
		this.type = type;
		return this;
	}

	/** {@inheritDoc} */
	 
	@Override
	public ResourceService addSystemType(Integer systemType) {
		this.systemType = systemType;
		return this;
	}

	/** {@inheritDoc} */
	 
	@Override
	public ResourceService addAvailable(String available) {
		this.available = available;
		return this;
	}

	/** {@inheritDoc} */
	 
	@Override
	public ResourceService addParentCode(String parentCode) {
		this.parentCode = parentCode;
		return this;
	}
    
    /**
     * 设置查询条件-用户ID(主键)
     */
    @Override
    public ResourceService addUserId(String userId)
    {
        this.userId = userId;
        return this;
    }
    
    /**
     * 设置查询条件-角色ID(主键)
     */
    @Override
    public ResourceService addRoleId(String roleId)
    {
        this.roleId = roleId;
        return this;
    }
    
    /**
     * 设置查询条件-资源ID(主键)
     */
    @Override
    public ResourceServiceFacade addQueryId(String id)
    {
        this.id = id;
        return this;
    }
    
    /**
     * 设置查询条件-资源名称
     */
    @Override
    public ResourceServiceFacade addName(String name)
    {
        this.name = name;
        return this;
    }
    
    /**
     * 设置处理对象（保存、修改）
     */
    @Override
    public ResourceServiceFacade setObject(Resource resource)
    {
        this.resource = resource;
        return this;
    }
    
    /**
     * 设置处理对象（保存、修改）
     */
    @Override
    public ResourceServiceFacade addIsLeaf(Integer isLeaf)
    {
        this.isLeaf = isLeaf;
        return this;
    }
    
    /*
     * moveResource
     * 查询资源信息
     */
    @Override
    public SysQueryService<SysResourceResource> queryResource(String[] fields)
        throws Exception
    {
        // 根据资源id查询角色信息ids
        RoleResourceFacade roleResourceFacade = context.getBean(RoleResourceFacade.class).addQueryId(RESOURCE_ID, id);
        SysQueryService<SysResourceResource> queryPageService =
            context.getBean(QueryResource.class).addQueryParam(id, fields).addRoleResourceFacade(roleResourceFacade);
        queryPageService.execute();
        return queryPageService;
    }
    
    /*
     * 查询资源信息
     */
    @Override
    public SysQueryService<?> queryResource(Integer pageNum, Integer pageSize, DataType dataType, String[] fields)
        throws Exception
    {
        SysQueryService<?> queryPageService = null;
        //分页查询返回page
        if (DataType.Page.equals(dataType) || null == dataType)
        {
            logger.info("资源分页查询," + dataType);
            queryPageService = context.getBean(QueryPageResource.class)
                .setQueryParams(pageNum, pageSize, fields)
                .setQueryParams(name, type, systemType, available, parentCode);
        }
        
        //查询所有返回list
        else if (DataType.List.equals(dataType))
        {
            logger.info("按条件查询资源集合," + dataType);
            queryPageService =
                context.getBean(QueryListResource.class).addQuery(fields).addRoleId(roleId).addUserId(userId).addIsLeaf(
                    isLeaf).addAvailable(available);
            
        }
        
        //树形数据查询返回tree
        else if (DataType.Tree.equals(dataType))
        {
            logger.info("查询资源树," + dataType);
            queryPageService = context.getBean(QueryTreeResource.class).setQueryParams(systemType).setAvailable(available);
        }
        queryPageService.execute();
        return queryPageService;
    }
    
    /*
     * 保存资源信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaveResource saveResource()
        throws Exception
    {
        
        SaveResource service = context.getBean(SaveResource.class).setObject(resource);
        // 执行器
        service.execute();
        return service;
    }
    
    /*
     * 修改资源信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService moveResource()
        throws Exception
    {
        BaseService service = context.getBean(MoveResource.class).setObject(resource).setResourceId(id);
        // 执行器
        service.execute();
        return service;
    }
    
    /*
     * 修改资源信息状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService moveResource(List<StateInstance> obj)
        throws Exception
    {
        BaseService service = context.getBean(MoveResourceState.class).addObject(obj);
        // 执行器
        service.execute();
        return service;
    }

	
}
