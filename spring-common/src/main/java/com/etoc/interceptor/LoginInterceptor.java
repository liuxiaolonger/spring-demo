/*
 * 文 件 名:  LoginInterceptor.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ETOC-ChenChao
 * 修改时间:  Jun 20, 2018
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.etoc.SpringContextHolder;
import com.etoc.properties.WeixinProperties;
import com.etoc.util.WeixinUtil;
import com.etoc.vo.Token;
import com.etoc.vo.User;

/**
 * 登录拦截器
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, Jun 20, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LoginInterceptor implements HandlerInterceptor {

	/** {@inheritDoc} */

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		WeixinProperties weixinProperties = SpringContextHolder.staticBean(WeixinProperties.class);
		HttpSession session = request.getSession();
		//System.out.println("拦截器session: " + session.getId());
		User user = (User)session.getAttribute("user");
		//System.out.println(request.getRequestURI() + ", 被拦截！");
		if (user == null) {
			// fetch或者ajax请求，不能直接redirect，客户端使用window.loaction.href实现跳转；浏览器跳转请求，可以使用sendRedirect();
			//response.sendRedirect(WeixinUtil.oauth2AuthorizeURL(weixinProperties.getCorpId(), weixinProperties.getRedirectUri(), WeixinUtil.SCOPE_SNSAPI_PRIVATEINFO, weixinProperties.getAgentId(), session.getId()));
			response.setHeader("Redirect-URI", WeixinUtil.oauth2AuthorizeURL(weixinProperties.getCorpId(), weixinProperties.getRedirectUri(), WeixinUtil.SCOPE_SNSAPI_PRIVATEINFO, weixinProperties.getAgentId(), session.getId()));
			response.setHeader("Session-Status", "timeout");
			response.sendError(518, "session timeout.");
			return false;
		}else {
			return true;
		}
	}

	/** {@inheritDoc} */

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		Token token = JSON.parseObject(request.getHeader("Token"), Token.class);
		token.setInvalide(DateUtils.addSeconds(new Date(), session.getMaxInactiveInterval()));
		response.addHeader("Token", JSON.toJSONString(token));
	}

	/** {@inheritDoc} */

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
