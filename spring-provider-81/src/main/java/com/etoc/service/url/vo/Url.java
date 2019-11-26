package com.etoc.service.url.vo;

/**
 * 
 * url返回到页面的vo对象 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Url {
	private String urlFilterId;

	private String name;

	private String method;

	private String url;

	private String roles;

	private String permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getUrlFilterId() {
		return urlFilterId;
	}

	public void setUrlFilterId(String urlFilterId) {
		this.urlFilterId = urlFilterId;
	}

	@Override
	public String toString() {
		return "Url [urlFilterId=" + urlFilterId + ", name=" + name + ", method=" + method + ", url=" + url + ", roles="
				+ roles + ", permissions=" + permissions + "]";
	}

}
