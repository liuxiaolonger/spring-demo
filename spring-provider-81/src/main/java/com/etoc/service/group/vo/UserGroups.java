package com.etoc.service.group.vo;

import java.util.List;

/**
 * 用户组返回到页面的vo对象
 * 
 * @author longlong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserGroups {

	// 用户组id
	private String userGroupId;
	// 用户组名称
	private String userGroupName;
	// 用户组描述
	private String userGroupDesc;
	// 用户组状态
	private String available;

	// 用户组关联用户ids
	private List<String> userIds;

	public String getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupName() {
		return this.userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public String getUserGroupDesc() {
		return this.userGroupDesc;
	}

	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}

	public List<String> getUserIds() {
		return this.userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "UserGroups [userGroupId=" + this.userGroupId + ", userGroupName=" + this.userGroupName + ", userGroupDesc=" + this.userGroupDesc + ", available=" + this.available + ", userIds="
				+ this.userIds + "]";
	}

}
