package com.etoc.service.organization.vo;

/**
 * 
 * .组织机构入参
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Organization
{
    /*
     * 主键
     */
    private String organizationId;
    
    /*
     * 机构名称
     */
    private String name;
    
    /*
     * 机构编码
     */
    private String code;
    
    /*
     * 机构父编码
     */
    private String parentCode;
    
    /*
     * 状态
     */
    private String available;
    
    /*
     * 父编码路劲
     */
    private String parentCodes;
    
    /*
     * 排序
     */
    private Integer priority;
    
    public String getOrganizationId()
    {
        return organizationId;
    }
    
    public void setOrganizationId(String organizationId)
    {
        this.organizationId = organizationId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getParentCode()
    {
        return parentCode;
    }
    
    public void setParentCode(String parentCode)
    {
        this.parentCode = parentCode;
    }
    
    public String getAvailable()
    {
        return available;
    }
    
    public void setAvailable(String available)
    {
        this.available = available;
    }
    
    public String getParentCodes()
    {
        return parentCodes;
    }
    
    public void setParentCodes(String parentCodes)
    {
        this.parentCodes = parentCodes;
    }
    
    public Integer getPriority()
    {
        return priority;
    }
    
    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }
    
    @Override
    public String toString()
    {
        return "Organization [organizationId=" + organizationId + ", name=" + name + ", code=" + code + ", parentCode="
            + parentCode + ", available=" + available + ", parentCodes=" + parentCodes + ", priority=" + priority + "]";
    }
    
}
