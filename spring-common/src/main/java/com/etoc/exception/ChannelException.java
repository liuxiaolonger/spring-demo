/*
 * 文 件 名:  ChannelException.java
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
 * 异常
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Apr 28, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ChannelException extends RuntimeException {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -3447434181532314777L;
	
	public ChannelException(String message) {
		super(message);
	}
	
	public ChannelException(String message, Throwable cause) {
		super(message, cause);
	}

}
