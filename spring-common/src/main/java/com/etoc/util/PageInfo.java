/*
 * 文 件 名:  PageInfo2.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2018年12月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.util;
import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.Link;

/**
 * 自定义的PageInfo，继承com.github.pagehelper.PageInfo
 * 1.增加一个属性，links用于给前台返回接口信息
 * 2.addLink的函数用于给links添加link
 * 3.增加一个构造方法,初始化links
 * 
 * @author  Administrator,朱鹏
 * @version  [版本号, 2018年12月3日]
 * @version  [版本号, 2019年1月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PageInfo<T extends BaseResource> extends com.github.pagehelper.PageInfo<T>{
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 2649504381616491761L;
	
	private List<Link> links;

	/** 
	 * <默认构造函数>
	 */
	public PageInfo() {
		super();
		this.links = new ArrayList<Link>();
	}

	public List<Link> getLinks()
	{
		return links;
	}

	public void setLinks(List<Link> links)
	{
		this.links = links;
	}

	/**
	 * 给links添加link
	 * <功能详细描述>
	 * @param link
	 * @see [类、类#方法、类#成员]
	 */
	public void addLink(Link link) {
		this.links.add(link);
	}
	
	/**
	 * 构造方法，给links添加link
	 */
    public PageInfo(List<T> list,Class<T> clazz) throws Exception {
    	super(list);
    	this.links = new ArrayList<Link>();
    	clazz.newInstance().setLikes(this);
    }
	
	
	

}
