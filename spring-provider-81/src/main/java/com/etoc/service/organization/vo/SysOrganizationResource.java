package com.etoc.service.organization.vo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.etoc.constant.RelConst;
import com.etoc.service.organization.SysOrganizationController;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;


/**
 * 
 * .组织机构资源信息出参
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SysOrganizationResource extends BaseResource {

    //组织机构主键
	private String organizationId;
    //机构编码
	private String code;
    //机构名称
	private String name;
    //排序
	private Integer priority;
    //父编码
	private String parentCode;
    //父编码路劲
	private String parentCodes;
    //状态
	private String available;
	
    public String getOrganizationId()
    {
        return organizationId;
    }

    public void setOrganizationId(String organizationId)
    {
        this.organizationId = organizationId;
        add(linkTo(methodOn(SysOrganizationController.class).modify(organizationId,null)).withRel(RelConst.REL_MODIFY));
        add(linkTo(methodOn(SysOrganizationController.class).get(organizationId,null)).withRel(RelConst.REL_SELF));
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public String getParentCode()
    {
        return parentCode;
    }

    public void setParentCode(String parentCode)
    {
        this.parentCode = parentCode;
    }

    public String getParentCodes()
    {
        return parentCodes;
    }

    public void setParentCodes(String parentCodes)
    {
        this.parentCodes = parentCodes;
    }

    public String getAvailable()
    {
        return available;
    }

    public void setAvailable(String available)
    {
        this.available = available;
    }

    @Override
    public String toString()
    {
        return "SysOrganizationResource [organizationId=" + organizationId + ", code=" + code + ", name=" + name
            + ", priority=" + priority + ", parentCode=" + parentCode + ", parentCodes=" + parentCodes + ", available="
            + available + "]";
    }

	/** {@inheritDoc} */
	 
	@Override
	public void setLikes(PageInfo<?> page) {
		page.addLink(linkTo(methodOn(SysOrganizationController.class).search(null,null,null,null,null,null,null,null,null)).withRel(RelConst.REL_SEARCH));
		page.addLink(linkTo(methodOn(SysOrganizationController.class).add(null)).withRel(RelConst.REL_ADD));
		page.addLink(linkTo(methodOn(SysOrganizationController.class).batchOrganizationStatus(null)).withRel(RelConst.REL_STATE));
		
	}
}
