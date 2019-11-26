package com.etoc.util;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * http上下文工具类
 * @author ETOC-ChenChao
 *
 */
public class HttpContextUtil {

	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取域名
	 * @return
	 */
	public static String getDomain(){
		HttpServletRequest request = getHttpServletRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}

	/**
	 * 获取Origin请求头
	 * @return
	 */
	public static String getOrigin(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Origin");
	}
	
	/**
	 * 获取请求方式
	 * @return
	 */
	public static String getUserAgent(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("User-Agent");
	}
}
