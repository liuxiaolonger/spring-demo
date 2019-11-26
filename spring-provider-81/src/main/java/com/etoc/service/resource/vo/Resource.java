
package com.etoc.service.resource.vo;

/**
 * 
 * 资源返回到页面的vo对象 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Resource {
	private String resourceId;

	private String code;

	private String name;

	private String type;

	private Integer systemType;

	private String icon;

	private Integer priority;

	private String url;

	private String parentCode;

	private String parentCodes;

	private String permission;

	private String available;

	public String getResourceId() {
		return resourceId;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Integer getSystemType() {
		return systemType;
	}

	public String getIcon() {
		return icon;
	}

	public Integer getPriority() {
		return priority;
	}

	public String getUrl() {
		return url;
	}

	public String getParentCode() {
		return parentCode;
	}

	public String getParentCodes() {
		return parentCodes;
	}

	public String getPermission() {
		return permission;
	}

	public String getAvailable() {
		return available;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public void setParentCodes(String parentCodes) {
		this.parentCodes = parentCodes;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Resource [resourceId=" + resourceId + ", code=" + code + ", name=" + name + ", type=" + type
				+ ", systemType=" + systemType + ", icon=" + icon + ", priority=" + priority + ", url=" + url
				+ ", parentCode=" + parentCode + ", parentCodes=" + parentCodes + ", permission=" + permission
				+ ", available=" + available + "]";
	}
}
