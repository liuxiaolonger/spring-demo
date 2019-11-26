package com.longer.service.userRes.vo;

public class UserResource {
	private String id;

	private String userId;

	private String resourceId;

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String toString() {
		return "SysUsers [id=" + this.id + ", userId=" + this.userId + ", resourceId=" + this.resourceId + "]";
	}

}
