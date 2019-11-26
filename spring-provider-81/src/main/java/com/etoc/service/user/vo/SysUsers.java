package com.etoc.service.user.vo;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户返回到页面的vo对象
 * 
 * @author longlong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysUsers {

	// 用户主键id
	private String userId;
	// 用户登录名
	private String loginName;
	// 登录密码
	private String loginPsw;
	// 盐
	private String salt;
	// 用户名
	private String userName;
	// 上次登录时间
	private String loginTime;
	// 状态
	private String status;
	// 机构id
	private String organizationId;

	// 用户手机号码
	private String userPhone;
	// 用户邮箱
	private String userMail;
	// 微信账号
	private String userWechat;
	// 备注
	private String userNote;
	// 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	// 用户关联的角色ids
	private List<String> roleIds;
	// 用户关联的资源ids
	private List<String> resourceIds;
	// 用户关联的用户组ids
	private List<String> groupIds;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoleIds() {
		return this.roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getResourceIds() {
		return this.resourceIds;
	}

	public void setResourceIds(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	public List<String> getGroupIds() {
		return this.groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}

	public String getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getLoginPsw() {
		return this.loginPsw;
	}

	public void setLoginPsw(String loginPsw) {
		this.loginPsw = loginPsw;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserMail() {
		return this.userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserWechat() {
		return this.userWechat;
	}

	public void setUserWechat(String userWechat) {
		this.userWechat = userWechat;
	}

	public String getUserNote() {
		return this.userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "SysUsers [userId=" + this.userId + ", loginName=" + this.loginName + ", loginPsw=" + this.loginPsw + ", salt=" + this.salt + ", userName=" + this.userName + ", loginTime="
				+ this.loginTime + ", status=" + this.status + ", organizationId=" + this.organizationId + ", userPhone=" + this.userPhone + ", userMail=" + this.userMail + ", userWechat="
				+ this.userWechat + ", userNote=" + this.userNote + ", createTime=" + this.createTime + ", roleIds=" + this.roleIds + ", resourceIds=" + this.resourceIds + ", groupIds="
				+ this.groupIds + "]";
	}

}
