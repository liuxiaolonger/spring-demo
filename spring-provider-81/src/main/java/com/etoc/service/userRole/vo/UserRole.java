package com.etoc.service.userRole.vo;

public class UserRole {

	private String id;

	private String userId;

	private String roleId;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + this.id + ", userId=" + this.userId + ", roleId=" + this.roleId + "]";
	}

}
