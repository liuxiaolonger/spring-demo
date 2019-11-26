package com.longer.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.longer.config.DBContextHolder;

/**
 * 
 * @author Admin
 *
 */
@Aspect
@Component
public class WriteOnlyConnectionInterceptor implements Ordered {

	public static final Logger logger = LoggerFactory.getLogger(WriteOnlyConnectionInterceptor.class);

	@Around("@annotation(com.longer.annotation.WriteOnlys)")
	public Object proceed(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
		try {
			logger.info("set database connection to write only");
			DBContextHolder.master();
			Object result = proceedingJoinPoint.proceed();
			return result;
		} finally {
			DBContextHolder.clearDbType();
			logger.info("restore database connection");
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
