package com.etoc.util;

/**
 * 用户代理工具类
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Aug 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UserAgentUtil {
	
	/**
	 * 定义移动端请求的所有可能类型
	 */
	private final static String[] MOBILE_KEYWORDS = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };

	/**
	 * 判断User-Agent 是不是来自于手机
	 * 
	 * @param ua         用于代理请求头信息
	 * @return           如果是移动端，则返回true；否则，返回false
	 */
	public static boolean isMobile(String ua) {
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : MOBILE_KEYWORDS) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
}
