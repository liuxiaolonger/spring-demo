package com.etoc.util;

import org.springframework.hateoas.Resource;


/**
 * 
 * 自定义BaseResource，继承 org.springframework.hateoas.Resource
 *其他查询返回的Resource继承此类
 * 主要目的：增加无参的构造方法，重写getContent()，以免页面显示空对象
 *
 * @author  朱鹏
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseResource extends Resource<Object>
{
	
	/**
	 * <默认构造函数>
	 */
	protected BaseResource()
	{
		super(new Object());
	}
	
	@Override
	public Object getContent() 
	{
		return null;
	}
	
	/**
	 * 往page对象里添加links
	 * <功能详细描述>
	 * @param page
	 * @see [类、类#方法、类#成员]
	 */
	public abstract void setLikes(PageInfo<?> page);
	
}
