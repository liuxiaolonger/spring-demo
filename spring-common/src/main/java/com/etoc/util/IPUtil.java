package com.etoc.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具类
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Aug 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class IPUtil {
	private static Logger logger = LoggerFactory.getLogger(IPUtil.class);

	/**
	 * 获取IP地址
	 * 
	 * 如果使用Nginx等反向代理软件，则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 * @param request        HttpServletRequest对象
	 * @return               如果无法解析，则返回null
	 * @see [类、类#方法、类#成员]
	 */
	public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	logger.warn("无法解析请求源的IP地址", e);
        }
        return ip;
    }
	
}
