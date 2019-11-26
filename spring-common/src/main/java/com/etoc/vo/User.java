/*
 * 文 件 名:  User.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Jun 20, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.vo;

import java.util.Date;

/**
 * 会话中存放的用户信息
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Jun 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class User {
   private String id;
   private String organizationId;
   private String departmentId;
   private String loginName;
   private String loginPsw;
   private String salt;
   private Date loginTime;
   private String userName;
   private Date createTime;
   private String userMobile;
   private String userMail;
   private String userWechat;
   private String userNote;
   private Integer status;
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPsw() {
		return loginPsw;
	}
	public void setLoginPsw(String loginPsw) {
		this.loginPsw = loginPsw;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserWechat() {
		return userWechat;
	}
	public void setUserWechat(String userWechat) {
		this.userWechat = userWechat;
	}
	public String getUserNote() {
		return userNote;
	}
	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
