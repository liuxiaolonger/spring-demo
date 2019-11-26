package com.etoc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Admin
 * @Target允许在那里使用
 * @Retention表示允许获取方式
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotation {

	String value() default "";

	int id() default 10;
}

class testDemo {
	@Annotation(value = "老子最帅", id = 1000)
	public void add() {

	}
}