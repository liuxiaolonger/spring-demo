package com.longer.service.roleRes.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.util.UUIDUtil;
import com.longer.base.BaseService;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysRoleResource;
import com.longer.service.roleRes.AbsRoleResourceService;
import com.longer.service.roleRes.RoleResourceService;
import com.longer.service.roleRes.vo.RoleResource;

/**
 * 
 * 角色资源信息调度器
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("roleResourceFacade")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoleResourceFacade extends AbsRoleResourceService implements RoleResourceService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private String id;
    
    private String type;
    
    // 过滤资源是否是叶子节点 1是   0否
    private Integer  isLeaf;
    
    // 用户、资源ID集
    private List<String> ids = new ArrayList<>();
    
    @Override
    public RoleResourceFacade addIsLeaf(Integer isLeaf) {
    	  this.isLeaf = isLeaf;
          return this;
    }
    
    @Override
    public RoleResourceFacade addQueryId(String type, String id)
    {
        this.type = type;
        this.id = id;
        return this;
    }
    
    @Override
    public RoleResourceFacade setObject(String type, String id, List<String> ids)
    {
        this.type = type;
        this.id = id;
        this.ids = ids;
        return this;
    }
    
    @Override
    public SysQueryListService<RoleResource> queryRoleResource()
        throws Exception
    {
        QueryListRoleResource queryListRoleResource = context.getBean(QueryListRoleResource.class)
        		.addIsLeaf(isLeaf);
        if (ROLE_ID.equals(type))
        {
            queryListRoleResource.addRoleId(id);
        }
        else if (RESOURCE_ID.equals(type))
        {
            queryListRoleResource.addResourceId(id);
        }
        else
        {
            logger.error("添加时type参数错误！！！");
        }
        queryListRoleResource.execute();
        
        return queryListRoleResource;
    }
   
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService saveRoleResource()
        throws Exception
    {
        // 保存关联表的bean
        SaveListRoleResource saveListRoleResource = context.getBean(SaveListRoleResource.class);
        List<SysRoleResource> lists = new ArrayList<SysRoleResource>();// 存放要保存的关联表对象信息
        // 当ids不为空集合时给保存动作传参
        if (CollectionUtils.isNotEmpty(ids))
        {
            ids.forEach(relateId -> {
                SysRoleResource sysRoleResource = new SysRoleResource();
                sysRoleResource.setId(UUIDUtil.getUUID());
                if (ROLE_ID.equals(type))
                {
                    // 根据角色保存资源关联表         
                    sysRoleResource.setResourceId(relateId);
                    sysRoleResource.setRoleId(id);
                }
                else if (RESOURCE_ID.equals(type))
                {
                    // 根据资源保存角色关联表         
                    sysRoleResource.setResourceId(id);
                    sysRoleResource.setRoleId(relateId);
                }
                else
                {
                    logger.error("添加时type参数错误！！！");
                }
                lists.add(sysRoleResource);
            });
        }
        saveListRoleResource.setObject(lists);
        saveListRoleResource.execute();
        return saveListRoleResource;
        
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService moveRoleResource()
        throws Exception
    {
        //实例化修改对象
        MoveListRoleResource moveListRoleResource = context.getBean(MoveListRoleResource.class);
        // 实例化删除对象
        RemoveListRoleResource removeListRoleResource = context.getBean(RemoveListRoleResource.class);
        //实例化新增对象 
        SaveListRoleResource saveRoleResource = context.getBean(SaveListRoleResource.class);
        //当关联表的ids为空时设置id为空
        String id = this.ids == null ? null : this.id;
        if (ROLE_ID.equals(type))
        {
            removeListRoleResource.addRoleId(id);
        }
        else if (RESOURCE_ID.equals(type))
        {
            removeListRoleResource.addResourceId(id);
        }
        else
        {
            logger.error("添加时type参数错误！！！");
        }
        
        List<SysRoleResource> lists = new ArrayList<SysRoleResource>();
        // 当ids不为空集合时给保存动作传参
        if (CollectionUtils.isNotEmpty(ids))
        {
            ids.forEach(relateId -> {
                SysRoleResource sysRoleResource = new SysRoleResource();
                sysRoleResource.setId(UUIDUtil.getUUID());
                if (ROLE_ID.equals(type))
                {
                    // 根据角色保存资源关联表         
                    sysRoleResource.setResourceId(relateId);
                    sysRoleResource.setRoleId(id);
                }
                else if (RESOURCE_ID.equals(type))
                {
                    // 根据资源保存角色关联表         
                    sysRoleResource.setResourceId(id);
                    sysRoleResource.setRoleId(relateId);
                }
                else
                {
                    logger.error("添加时type参数错误！！！");
                }
                lists.add(sysRoleResource);
            });
            saveRoleResource.setObject(lists);
        }
        
        moveListRoleResource.setSysRemoveService(removeListRoleResource);
        moveListRoleResource.setSysSaveService(saveRoleResource);
        
        moveListRoleResource.execute();
        return moveListRoleResource;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseService removeRoleResource()
        throws Exception
    {
        RemoveListRoleResource removeListRoleResource = context.getBean(RemoveListRoleResource.class);
        if (ROLE_ID.equals(type))
        {
            removeListRoleResource.addRoleId(id);
        }
        else if (RESOURCE_ID.equals(type))
        {
            removeListRoleResource.addResourceId(id);
        }
        else
        {
            logger.error("添加时type参数错误！！！");
        }
        removeListRoleResource.execute();
        return removeListRoleResource;
    }
    
}
