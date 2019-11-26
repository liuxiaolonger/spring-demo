package com.etoc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户端和服务端会话令信息对象
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Jun 21, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Token implements Serializable {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 2060382779075958423L;

	//用户ID
	private String userId;
	
	//登录名称
	private String loginName;
	
	//微信授权
	private Boolean authorization;
	
	//账号认证
	private Boolean authentication;
	
	//唯一标识
	private String jsessionId;
	
	//过期时间
	private Date invalide;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Boolean getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Boolean authorization) {
		this.authorization = authorization;
	}

	public Boolean getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Boolean authentication) {
		this.authentication = authentication;
	}

	public String getJsessionId() {
		return jsessionId;
	}

	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}

	public Date getInvalide() {
		return invalide;
	}
	
	public void setInvalide(Date invalide) {
		this.invalide = invalide;
	}

	public Token(String userId, String loginName, Boolean authorization, Boolean authentication, String jsessionId,
			Date invalide) {
		super();
		this.userId = userId;
		this.loginName = loginName;
		this.authorization = authorization;
		this.authentication = authentication;
		this.jsessionId = jsessionId;
		this.invalide = invalide;
	}

	public Token() {
		super();
	}

	@Override
	public String toString() {
		return "Token [userId=" + userId + ", loginName=" + loginName + ", authorization=" + authorization
				+ ", authentication=" + authentication + ", jsessionId=" + jsessionId + ", invalide=" + invalide + "]";
	}

}
