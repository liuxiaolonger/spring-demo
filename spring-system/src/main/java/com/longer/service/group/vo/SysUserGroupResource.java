/*
 * 文 件 名:  SysUserGroupResource.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2018年10月29日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.group.vo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import com.etoc.constant.RelConst;
import com.etoc.constant.RelConst.RelUserGroupConst;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;
import com.longer.service.group.SysUserGroupController;

import com.longer.service.user.UserController;

/**
 * 分页返回资源
 * 
 * @author longer
 * @version [版本号, 2018年10月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysUserGroupResource extends BaseResource {

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

		add(linkTo(methodOn(SysUserGroupController.class).get(userGroupId, null)).withRel(RelConst.REL_SELF));
		add(linkTo(methodOn(SysUserGroupController.class).modify(userGroupId, null)).withRel(RelConst.REL_MODIFY));
		add(linkTo(methodOn(SysUserGroupController.class).remove(userGroupId)).withRel(RelConst.REL_REMOVE));

		add(linkTo(methodOn(SysUserGroupController.class).getUsers(userGroupId, null)).withRel(RelConst.REL_RELATED));

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
		return "SysUserGroupResource [userGroupId=" + this.userGroupId + ", userGroupName=" + this.userGroupName + ", userGroupDesc=" + this.userGroupDesc + ", available=" + this.available
				+ ", userIds=" + this.userIds + "]";
	}

	/** {@inheritDoc} */
	 
	@Override
	public void setLikes(PageInfo<?> page) {
	 
        page.addLink(linkTo(methodOn(UserController.class).search(null, null, null, null, null, null, null,null)).withRel(RelUserGroupConst.REL_SEARCH_USER));
		page.addLink(linkTo(methodOn(SysUserGroupController.class).searchGroup(null, null, null, null, null)).withRel(RelConst.REL_SEARCH));
		page.addLink(linkTo(methodOn(SysUserGroupController.class).add(null)).withRel(RelConst.REL_ADD));
		page.addLink(linkTo(methodOn(SysUserGroupController.class).batchUserGroup(null)).withRel(RelConst.REL_STATE));
	}

}
