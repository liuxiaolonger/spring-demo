package com.longer.aspect;


import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etoc.util.HttpContextUtil;
import com.etoc.util.IPUtil;
import com.etoc.util.JsonUtil;
import com.etoc.util.UUIDUtil;
import com.etoc.util.UserAgentUtil;
import com.longer.annotation.SysLog;
import com.longer.dao.mapper.SysUserMapper;
import com.longer.dao.mapper.SystemLogMapper;
import com.longer.dao.model.SysUser;
import com.longer.dao.model.SystemLog;




/**
 * 
 * 运用自定义日志注解和切面自动处理日志的类
 * <功能详细描述>
 *
 * @author  chuyh
 * @version  [版本号, 2018年8月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
/*
 * @Aspect
 * 
 * @Component
 */
public class SysLogAspect {

	@Autowired
	private SystemLogMapper systemLogMapper;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * <code>@annotation</code>是针对方法的注解
	 * <code>@Pointcut("@annotation(*SysLog)")</code>表示带有<code>@SysLog</code>的所有方法需要被执行"AOP"
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
	@Pointcut("@annotation(com.longer.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time, "");
		return result;
	}
	
	@AfterThrowing(value = "logPointCut()", throwing = "ex")
	public void afterThrowing(JoinPoint point, Throwable ex) {
		//保存日志
		saveSysLog(point, 0, ex.toString());
	}
	
	private void saveSysLog(JoinPoint joinPoint, long time, String ex) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SystemLog sysLog = new SystemLog();
		sysLog.setId(UUIDUtil.getUUID());
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			sysLog.setLogDesc(syslog.value());//获取日志的描述
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setModual(className);
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		StringBuilder params = new StringBuilder();
		for(Object object : args) {
			if (object instanceof HttpServletRequest
				|| object instanceof HttpServletResponse) {
				continue;
			}
			params.append(JsonUtil.beanToJson(object))
				.append(",");
		}
		if(StringUtils.isNotEmpty(params)) {
			params = params.deleteCharAt(params.length()-1);
		}
		sysLog.setParams(params.toString());
		sysLog.setExceptions(ex);

		//获取request
		HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
		sysLog.setReqUrl(request.getRequestURI());
		//设置IP地址
		sysLog.setReqIp(IPUtil.getIpAddr(request));
		
		//获取终端类型
		boolean isMobile = UserAgentUtil.isMobile(HttpContextUtil.getUserAgent());
		sysLog.setTerminal(isMobile ? 1 : 0);//0:web电脑，1:phone手机

		//用户名
		String userID = (String)request.getAttribute("id");
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(userID);
		if(sysUser!=null) {
			sysLog.setUserName(sysUser.getUserName());//
		}
		sysLog.setUserId(userID);
		sysLog.setCreater(userID);
		sysLog.setLogLevel(3);//0:管理员用户、1:安全管理员工、2:审计人员、3:普通用户 由于没有用户级别设置 故默认为3
		sysLog.setCreateTime(new Date());
		//保存系统日志
		systemLogMapper.insertAll(sysLog);
	}

}
