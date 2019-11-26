/*
 * 文 件 名:  SysResourceResource.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2018年10月25日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.resource.vo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import com.etoc.constant.RelConst;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;
import com.longer.service.resource.ResourceController;

/**
 * 
 * resource资源 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SysResourceResource extends BaseResource {

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
	
	private List<String> roleIds;

	public List<String> getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds)
    {
        this.roleIds = roleIds;
    }

    public String getResourceId() {
		// 根据id查询资源，可选择需要返回的字段
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		// 根据id查询资源
		add(linkTo(methodOn(ResourceController.class).get(resourceId, null)).withRel(RelConst.REL_SELF));
		// 根据id修改资源
		add(linkTo(methodOn(ResourceController.class).modify(resourceId, null)).withRel(RelConst.REL_MODIFY));
		this.resourceId = resourceId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSystemType() {
		return this.systemType;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentCodes() {
		return this.parentCodes;
	}

	public void setParentCodes(String parentCodes) {
		this.parentCodes = parentCodes;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "SysResourceResource [resourceId=" + this.resourceId + ", code=" + this.code + ", name=" + this.name + ", type=" + this.type + ", systemType=" + this.systemType + ", icon=" + this.icon
				+ ", priority=" + this.priority + ", url=" + this.url + ", parentCode=" + this.parentCode + ", parentCodes=" + this.parentCodes + ", permission=" + this.permission + ", available="
				+ this.available + "]";
	}

	/** {@inheritDoc} */

	@Override
	public void setLikes(PageInfo<?> page) {

		/**
		 * 设置page同级资源
		 * 
		 * @param list
		 * @see [类、类#方法、类#成员]
		 */
		// 添加资源
		page.addLink(linkTo(methodOn(ResourceController.class).add(null)).withRel(RelConst.REL_ADD));
		// 修改资源的状态
		page.addLink(linkTo(methodOn(ResourceController.class).batchResouceStatus(null)).withRel(RelConst.REL_STATE));
		// 查询 返回List,Page,Tree
		page.addLink(linkTo(methodOn(ResourceController.class).search(null, null, null, null, null, null, null, null, null)).withRel(RelConst.REL_SEARCH));
	}

}
