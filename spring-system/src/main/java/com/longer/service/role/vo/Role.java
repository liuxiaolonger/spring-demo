package com.longer.service.role.vo;

import java.util.List;

/**
 * 
 *角色返回到页面的vo对象
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Role
{
    private String roleId;
    
    private String roleName;
    
    private Integer systemType;
    
    private String description;
    
    private String available;
    
    private List<String> userIds;
    
    public List<String> getUserIds()
    {
        return userIds;
    }
    
    public List<String> getResourceIds()
    {
        return resourceIds;
    }
    
    public void setUserIds(List<String> userIds)
    {
        this.userIds = userIds;
    }
    
    public void setResourceIds(List<String> resourceIds)
    {
        this.resourceIds = resourceIds;
    }
    
    private List<String> resourceIds;
    
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
    
    public String getRoleId()
    {
        return roleId;
    }
    
    public void setRoleId(String roleId)
    {
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
    
    @Override
    public String toString()
    {
        return "Role [roleId=" + roleId + ", roleName=" + roleName + ", systemType=" + systemType + ", description="
            + description + ", available=" + available + ", userIds=" + userIds + ", resourceIds=" + resourceIds + "]";
    }
    
}
