/*
 * 文 件 名:  ReactProperties.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Jun 20, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 前端react的配置文件
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Jun 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//@ConfigurationProperties(prefix="react")
//@Component
public class ReactConfig {
	/**
	 * 手机端bms跳转地址
	 */
	private String transferUrl;
	
	public String getTransferUrl() {
		return transferUrl;
	}
	public void setTransferUrl(String transferUrl) {
		this.transferUrl = transferUrl;
	}
}
