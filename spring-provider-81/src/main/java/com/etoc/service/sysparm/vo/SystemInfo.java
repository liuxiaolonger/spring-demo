package com.etoc.service.sysparm.vo;

public class SystemInfo
{
    //系统参数主键
    private String systemId;
    //系统参数名称
    private String systemName;
    //系统参数键
    private String systemKey;
    //系统参数值
    private String systemVal;
    //系统参数值类型
    private String valType;
    //系统参数值长度
    private Integer valLength;
    //系统参数描述
    private String systemDesc;
    
    public String getSystemId()
    {
        return systemId;
    }
    
    public void setSystemId(String systemId)
    {
        this.systemId = systemId;
    }
    
    public String getSystemName()
    {
        return systemName;
    }
    
    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }
    
    public String getSystemKey()
    {
        return systemKey;
    }
    
    public void setSystemKey(String systemKey)
    {
        this.systemKey = systemKey;
    }
    
    public String getSystemVal()
    {
        return systemVal;
    }
    
    public void setSystemVal(String systemVal)
    {
        this.systemVal = systemVal;
    }
    
    public String getValType()
    {
        return valType;
    }
    
    public void setValType(String valType)
    {
        this.valType = valType;
    }
    
    public Integer getValLength()
    {
        return valLength;
    }
    
    public void setValLength(Integer valLength)
    {
        this.valLength = valLength;
    }
    
    public String getSystemDesc()
    {
        return systemDesc;
    }
    
    public void setSystemDesc(String systemDesc)
    {
        this.systemDesc = systemDesc;
    }
    
    @Override
    public String toString()
    {
        return "SystemInfo [systemId=" + systemId + ", systemName=" + systemName + ", systemKey=" + systemKey
            + ", systemVal=" + systemVal + ", valType=" + valType + ", valLength=" + valLength + ", systemDesc="
            + systemDesc + "]";
    }
    
}
