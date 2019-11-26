/*
 * 文 件 名:  WeixinProperties.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Jun 20, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信属性配置文件
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Jun 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix="weixin")
@Component
public class WeixinProperties {
	/**
	 * 企业ID
	 */
	private String corpId;
	/**
	 * 应用凭证秘钥
	 */
	private String agentSecret;
	/**
	 * 企业应用的id
	 */
	private String agentId;
	/**
	 * 重定向地址
	 */
	private String redirectUri;
	
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getAgentSecret() {
		return agentSecret;
	}
	public void setAgentSecret(String agentSecret) {
		this.agentSecret = agentSecret;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	
}