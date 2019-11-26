package com.etoc.service.sysparm.vo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.etoc.constant.RelConst;
import com.etoc.service.sysparm.SystemInfoController;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;

/**
 * 
 * 系统参数出参 <功能详细描述>
 * 
 * @author chuyh
 * @version [版本号, 2019年1月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SystemInfoResource extends BaseResource {
	// 主键
	private String systemId;
	// 参数名称
	private String systemName;
	// 参数键
	private String systemKey;
	// 参数值
	private String systemVal;
	// 参数类型
	private String valType;
	// 参数长度
	private Integer valLength;
	// 参数描述
	private String systemDesc;


	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
		add(linkTo(methodOn(SystemInfoController.class).getBySystemId(systemId, null)).withRel(RelConst.REL_SELF));
		add(linkTo(methodOn(SystemInfoController.class).modifySystemInfo(systemId, null)).withRel(RelConst.REL_MODIFY));

	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemKey() {
		return systemKey;
	}

	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	public String getSystemVal() {
		return systemVal;
	}

	public void setSystemVal(String systemVal) {
		this.systemVal = systemVal;
	}

	public String getValType() {
		return valType;
	}

	public void setValType(String valType) {
		this.valType = valType;
	}

	public Integer getValLength() {
		return valLength;
	}

	public void setValLength(Integer valLength) {
		this.valLength = valLength;
	}

	public String getSystemDesc() {
		return systemDesc;
	}

	public void setSystemDesc(String systemDesc) {
		this.systemDesc = systemDesc;
	}

	@Override
	public String toString() {
		return "SystemInfoResource [systemId=" + this.systemId + ", systemName=" + this.systemName + ", systemKey=" + this.systemKey + ", systemVal=" + this.systemVal + ", valType=" + this.valType
				+ ", valLength=" + this.valLength + ", systemDesc=" + this.systemDesc + "]";
	}

	/** {@inheritDoc} */
	 
	@Override
	public void setLikes(PageInfo<?> page) {
		page.addLink(linkTo(methodOn(SystemInfoController.class).searchSystem(null, null, null, null, null, null, null, null)).withRel(RelConst.REL_SEARCH));
		page.addLink(linkTo(methodOn(SystemInfoController.class).addSystemInfo(null)).withRel(RelConst.REL_ADD));
	}

}