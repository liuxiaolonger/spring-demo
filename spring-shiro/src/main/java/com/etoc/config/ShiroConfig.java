package com.etoc.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.etoc.shiro.LoginRealm;
import com.etoc.shiro.MyShiroRealm;
import com.etoc.shiro.filter.JWTFilter;

/**
 * 
 * @author Admin
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * 先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
	 */
	@Bean
	public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		//SecurityManager来管理内部组件实例，并通过它来提供安全管理的各种服务
		factoryBean.setSecurityManager(securityManager);
		
		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<>();
		// 设置我们自定义的JWT过滤器
		filterMap.put("jwt", new JWTFilter());
		factoryBean.setFilters(filterMap);
				
		// 设置无权限时跳转的 url;
		factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
		Map<String, String> filterRuleMap = new HashMap<>();
		// 所有请求通过我们自己的JWT Filter
		filterRuleMap.put("/**", "jwt");
     	filterRuleMap.put("/captcha/","anon");
     	filterRuleMap.put("/login/","anon");
		// 访问 /unauthorized/** 不通过JWTFilter
		filterRuleMap.put("/unauthorized/**", "anon");
		factoryBean.setFilterChainDefinitionMap(filterRuleMap);
		
		return factoryBean;
	}

	/**
	 * 注入 securityManager安全管理器
	 */
	@Bean
	public SecurityManager securityManager(MyShiroRealm customRealm,LoginRealm loginRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		  Collection<Realm> realms = new ArrayList<>(); 
	        realms.add(customRealm);
	        realms.add(loginRealm);
	        securityManager.setRealms(realms);
		/*
		 * 关闭shiro自带的session，详情见文档
		 * http://shiro.apache.org/session-management.html#SessionManagement-
		 * StatelessApplications%28Sessionless%29
		 */
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		securityManager.setSubjectDAO(subjectDAO);
		return securityManager;
	}
	

	/**
	 * 添加注解支持
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		// 强制使用cglib，防止重复代理和可能引起代理出错的问题
		// https://zhuanlan.zhihu.com/p/29161098
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}