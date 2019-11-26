/*
 * 文 件 名:  userResource.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2018年10月25日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.service.user.vo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import com.etoc.constant.RelConst;
import com.etoc.service.organization.SysOrganizationController;
import com.etoc.service.resource.ResourceController;
import com.etoc.service.role.RoleController;
import com.etoc.service.user.UserController;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author longlong
 * @version [版本号, 2018年10月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysUserResource extends BaseResource {
	// 角色id占位符
	private final static String REL_ROLEID = ":roleId";
	// 角色id占位符
	private final static String REL_RESOURCEID = ":resourceId";

	// 用户主键id
	private String userId;
	// 用户登录名
	private String loginName;
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

		add(linkTo(methodOn(UserController.class).get(userId, null, null)).withRel(RelConst.REL_SELF));
		add(linkTo(methodOn(UserController.class).modify(userId, null)).withRel(RelConst.REL_MODIFY));
		add(linkTo(methodOn(UserController.class).resetPassword(userId)).withRel(RelConst.RelUserConst.REL_MODIFY_PASSWORD));
		add(linkTo(methodOn(UserController.class).getByIdResources(userId, null, null, null)).withRel(RelConst.RelUserConst.REL_RELATED_RESOURCE));
		add(linkTo(methodOn(UserController.class).getByIdRoles(userId, null)).withRel(RelConst.RelUserConst.REL_RELATED_ROLE));
		add(linkTo(methodOn(UserController.class).getByIdGroups(userId, null)).withRel(RelConst.RelUserConst.REL_RELATED_GROUP));
		
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

	public String toString() {
		return "SysUserResource [userId=" + this.userId + ", loginName=" + this.loginName + ", salt=" + this.salt + ", userName=" + this.userName + ", loginTime=" + this.loginTime + ", status="
				+ this.status + ", organizationId=" + this.organizationId + ", userPhone=" + this.userPhone + ", userMail=" + this.userMail + ", userWechat=" + this.userWechat + ", userNote="
				+ this.userNote + ", createTime=" + this.createTime + ", roleIds=" + this.roleIds + ", resourceIds=" + this.resourceIds + ", groupIds=" + this.groupIds + "]";
	}

	/** {@inheritDoc} */

	@Override
	public void setLikes(PageInfo<?> page) {
		page.addLink(linkTo(methodOn(UserController.class).search(null, null, null, null, null, null, null, null)).withRel(RelConst.REL_SEARCH));
		page.addLink(linkTo(methodOn(ResourceController.class).get(REL_RESOURCEID, null)).withRel(RelConst.REL_SELF));
		// 获取所有组织----获取机构树
		page.addLink(linkTo(methodOn(SysOrganizationController.class).search(null, null, null, null, null, null, null, null, null)).withRel(RelConst.RelUserConst.REL_SEARCH_ORGANIZATION));
		// 获取资源树
		page.addLink(linkTo(methodOn(ResourceController.class).search(null, null, null, null, null, null, null, null, null)).withRel(RelConst.RelUserConst.REL_SEARCH_RESOURCE));
		// 获取所有角色
		page.addLink(linkTo(methodOn(RoleController.class).search(null, null, null, null, null, null, null)).withRel(RelConst.RelUserConst.REL_SEARCH_ROLE));

		page.addLink(linkTo(methodOn(RoleController.class).getResourcesIdsById(REL_ROLEID, null, null)).withRel(RelConst.RelRoleConst.REL_RELATED_RESOURCE));
		page.addLink(linkTo(methodOn(UserController.class).add(null)).withRel(RelConst.REL_ADD));
		page.addLink(linkTo(methodOn(UserController.class).batchUserStatus(null)).withRel(RelConst.REL_STATE));

	}

}
