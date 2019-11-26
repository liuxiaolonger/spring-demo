package com.longer.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 自定义系统日志注解
 * <功能详细描述>
 *
 * @author  chuyh
 * @version  [版本号, 2018年8月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 日志内容描述
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	String value() default "";
}
