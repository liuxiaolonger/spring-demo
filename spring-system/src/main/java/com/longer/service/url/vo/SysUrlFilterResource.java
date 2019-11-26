package com.longer.service.url.vo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.etoc.constant.RelConst;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;
import com.longer.dao.model.SysUrlFilter;
import com.longer.service.url.SysUrlController;
/**
 * 
 * url资源
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SysUrlFilterResource extends BaseResource
{
    
    private String urlFilterId;
    
    private String name;
    
    private String method;
    
    private String url;
    
    private String roles;
    
    private String permissions;
    
    public String getUrlFilterId()
    {
        return urlFilterId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public String getRoles()
    {
        return roles;
    }
    
    public String getPermissions()
    {
        return permissions;
    }
    
    public void setUrlFilterId(String urlFilterId)
    {
        //根据urlId查询
        add(linkTo(methodOn(SysUrlController.class).getBySysUrlId(urlFilterId, null)).withRel(RelConst.REL_SELF));
        //修改url
        add(linkTo(methodOn(SysUrlController.class).modifySysUrl(urlFilterId, null)).withRel(RelConst.REL_MODIFY));
        //删除url
        add(linkTo(methodOn(SysUrlController.class).removeSysUrl(urlFilterId)).withRel(RelConst.REL_REMOVE));
        this.urlFilterId = urlFilterId;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setMethod(String method)
    {
        this.method = method;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public void setRoles(String roles)
    {
        this.roles = roles;
    }
    
    public void setPermissions(String permissions)
    {
        this.permissions = permissions;
    }
    
    @Override
    public SysUrlFilter getContent()
    {
        return null;
    }
    
    

	/** {@inheritDoc} */
	 
	@Override
	public void setLikes(PageInfo<?> page) {
		/**
	     * 设置资源
	     * @param list
	     * @see [类、类#方法、类#成员]
	     */
		page.addLink(linkTo(methodOn(SysUrlController.class).searchSysUrl(null,null,null,null,null)).withRel(RelConst.REL_SEARCH));
	        //添加url
		page.addLink(linkTo(methodOn(SysUrlController.class).add(null)).withRel(RelConst.REL_ADD));
	}
    
}