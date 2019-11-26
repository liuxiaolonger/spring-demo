/*
 * 文 件 名:  ChannelObjectNotFoundException.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Apr 28, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.exception;

/**
 * 对象未找到异常
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Apr 28, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ChannelObjectNotFoundException extends ChannelException {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 5960232934685784251L;
	/**
	 * 未找到的对象的类类型
	 */
	private Class<?> objectClass;
	
	public ChannelObjectNotFoundException(String message, Class<?> objectClass, Throwable cause) {
		super(message, cause);
		this.objectClass = objectClass;
	}
	
	public ChannelObjectNotFoundException(String message, Class<?> objectClass) {
		this(message, objectClass, null);
	}

	/**
	 * 获取未找到的对象类类型
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Class<?> getObjectClass() {
		return objectClass;
	}
	
}
