package com.designPatterns.Example.singleCase.evilMan;

/**
 * 线程安全，双重检测
 * 
 * @author Admin
 *
 */

public class EvilMan3 {
	public static void main(String[] args) {
		Singleton1 s = Singleton1.getInstance();
		Singleton1 ss = Singleton1.getInstance();
		System.out.println(s == ss);
		System.out.println(s.hashCode());
		System.out.println(ss.hashCode());
	}
}

/**
 * 在类加载的时候就创建，造成了内存的浪费
 * 
 * @author Admin
 *
 */
class Singleton2 {
	// 构造器私有化，外部不能new
	private Singleton2() {
	}
	// volatile 将值立即提交到主存
	private static volatile Singleton2 instance;

	// 提供一个共有的静态方法,返回实例对象，加入了同步处理的方法
	public static Singleton2 getInstance() {
		if (instance == null) {
			synchronized (Singleton2.class) {
				if (instance == null) {
					instance = new Singleton2();
				}
			}
		}
		return instance;
	}
}