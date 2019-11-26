package com.etoc.service.role.vo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import com.etoc.constant.RelConst;
import com.etoc.constant.RelConst.RelUserConst;
import com.etoc.model.SysRole;
import com.etoc.service.resource.ResourceController;
import com.etoc.service.role.RoleController;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;

/**
 * 
 * 角色资源
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SysRoleResource extends BaseResource
{
    
    private String roleId;
    
    private String roleName;
    
    private Integer systemType;
    
    private String description;
    
    private String available;
    
    private List<String> userIds;
    
    private List<String> resourceIds;
    
    public List<String> getResourceIds()
    {
        return resourceIds;
    }
    
    public void setResourceIds(List<String> resourceIds)
    {
        this.resourceIds = resourceIds;
    }
    
    public List<String> getUserIds()
    {
        return userIds;
    }
    
    public void setUserIds(List<String> userIds)
    {
        this.userIds = userIds;
    }
    
    @Override
    public SysRole getContent()
    {
        return null;
    }
    
    
    
    public String getRoleId()
    {
        return roleId;
    }
    
    public String getRoleName()
    {
        return roleName;
    }
    
    public Integer getSystemType()
    {
        return systemType;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public String getAvailable()
    {
        return available;
    }
    
    public void setRoleId(String roleId)
    {     
        add(linkTo(methodOn(RoleController.class).get(roleId, null, null)).withRel(RelConst.REL_SELF));
        add(linkTo(methodOn(RoleController.class).modify(roleId, null)).withRel(RelConst.REL_MODIFY));
        add(linkTo(methodOn(RoleController.class).getUsersIdsById(roleId)).withRel(RelConst.RelRoleConst.REL_RELATED_USER));
        add(linkTo(methodOn(RoleController.class).getResourcesIdsById(roleId,null,null)).withRel(RelConst.RelRoleConst.REL_RELATED_RESOURCE));
        this.roleId = roleId;
    }
    
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    
    public void setSystemType(Integer systemType)
    {
        this.systemType = systemType;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void setAvailable(String available)
    {
        this.available = available;
    }

	/** {@inheritDoc} */
	 
	@Override
	public void setLikes(PageInfo<?> page) {
		page.addLink(linkTo(methodOn(ResourceController.class).search(null, null, null, null, null,null, null, null, null)).withRel(RelUserConst.REL_SEARCH_RESOURCE));
		page.addLink(linkTo(methodOn(RoleController.class).search(null, null, null, null, null, null,null)).withRel(RelConst.REL_SEARCH));
		page.addLink(linkTo(methodOn(RoleController.class).add(null)).withRel(RelConst.REL_ADD));
		page.addLink(linkTo(methodOn(RoleController.class).batchRoleStatus(null)).withRel(RelConst.REL_STATE));

	}
    
}
