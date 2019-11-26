package com.etoc.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.etoc.util.TokenUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
@Component
public class TokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired	
    private TokenUtil tokenUtil;
    
    //过滤器的类型，它决定过滤器在请求的哪个生命周期中执行，这里定义为pre，代表会在请求被理由之前执行。
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // 可以在请求被路由之前调用
    }

    //过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行 最低为为-4
    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    //判断该过滤器是否需要被执行。这里我们直接返回了true，因此该过滤器对所有的请求都生效。实际运行中我们可以利用该函数
    //来指定过滤器的有效范围。
    @Override
    public boolean shouldFilter() {
    	RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
        {
            return false;
        }
        String uri = request.getRequestURI();
        if (uri.startsWith("/spring-shiro/login/") | uri.startsWith("/spring-shiro/captcha/")) {
			return false;
		}
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    //过滤器的具体执行逻辑。
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String username = "";
        String token = request.getParameter("token");// 尝试获取请求参数的token
        if (StringUtils.isNotEmpty(token))
        {
            // 从token中获取用户名
            username = tokenUtil.getUsernameFromToken(token);
            logger.info("--->>> TokenFilter {},{},{}", request.getMethod(), request.getRequestURL().toString(), username);

            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        }
        
        token = request.getHeader("token");// 尝试获取请求头的token
        if (StringUtils.isNotEmpty(token))
        {
            username = tokenUtil.getUsernameFromToken(token);
            logger.info("--->>> TokenFilter {},{},{}", request.getMethod(), request.getRequestURL().toString(), username);

            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        }
        
        Cookie[] cookies = request.getCookies();// 尝试获取Cookie的token
        if (ArrayUtils.isNotEmpty(cookies)) {
        	for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals("token"))
                {
                    token = cookie.getValue();
                    break;
                }
            }
		}
        if (StringUtils.isNotEmpty(token))
        {
            username = tokenUtil.getUsernameFromToken(token);
            logger.info("--->>> TokenFilter {},{},{}", request.getMethod(), request.getRequestURL().toString(), username);

            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        }
        logger.info("--->>> 非法请求,token为空!! {},{},{}", request.getMethod(), request.getRequestURL().toString(), username);
        ctx.setSendZuulResponse(false); //对请求进行路由
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("权限不够");
        ctx.set("isSuccess", false);
        return null;
    }

}