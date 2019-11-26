package com.etoc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 通用MVC配置
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ETOC-ChenChao
 * @version  [版本号, May 21, 2018]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * 跨域配置
	 * {@inheritDoc}
	 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
            .allowCredentials(true)
            .exposedHeaders("Token")// 暴露token请求头
            .maxAge(3600);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
//		registry.addInterceptor(new LoginInterceptor())
//			.addPathPatterns("/**")
//			.excludePathPatterns("/login/**","/wx/**");
	}
	
}
