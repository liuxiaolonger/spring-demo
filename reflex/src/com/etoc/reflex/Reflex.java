package com.etoc.reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;	

import com.etoc.bean.User;

/**
 * 1.不用new去创建对象2. 类的私有成员真的不可以被访问吗
 * 
 * @author Admin
 *
 */
public class Reflex {
	public static void main(String[] args) throws Exception {
		// 类的完整路径
//		Class<?> forName = Class.forName("com.etoc.bean.User");
//		User user= (User) forName.newInstance();
//		user.setAddr("香港九龙");
//		user.setAge(18);
//		user.setPwd("nicaia");
//		user.setUserName("刘天王");
//		System.out.println(user);

		// 构造函数
//		Class<?> forName = Class.forName("com.etoc.bean.User");
//		Constructor<?> constructor = forName.getConstructor(String.class);
//		User user = (User) constructor.newInstance("刘天王");
//		System.out.println(user);
		// 获取方法跟属性
//		Class<?> forName = Class.forName("com.etoc.bean.User");
//		// 得到所有的方法
//		Method[] methods = forName.getMethods();
//		// 得到公有的属性
//		Field[] fields = forName.getFields();
//		for (int i = 0; i < methods.length; i++) {
//			System.out.println(methods[i].getName());
//			System.out.println(methods[i].getReturnType());
//		}
//		for (int i = 0; i < fields.length; i++) {
//			Field field = fields[i];
//			System.out.println(field);
//		}

		// 设置属性的值
		Class<?> forName = Class.forName("com.etoc.bean.User");
		Field declaredField = forName.getDeclaredField("pwd");
		Object newInstance = forName.newInstance();
		// 允许访问私有成员
		declaredField.setAccessible(true);
		declaredField.set(newInstance, "123");
		User user = (User) newInstance;
		System.out.println(user);
	}
}
