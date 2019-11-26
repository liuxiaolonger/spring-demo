package com.designPatterns.Example.singleCase.evilMan;
/**
 * 线程安全，效率低下
 * @author Admin
 *
 */

public class EvilMan2 {
	public static void main(String[] args) {
		Singleton1  s=Singleton1.getInstance();
		Singleton1  ss=Singleton1.getInstance();
		System.out.println(s == ss);
		System.out.println(s.hashCode());
		System.out.println(ss.hashCode());
	}
}
/**
 * 在类加载的时候就创建，造成了内存的浪费
 * @author Admin
 *
 */
class Singleton1{
	//构造器私有化，外部不能new
	private Singleton1() {}
	//在本类内部创建对象实例
	private  static Singleton1 instance;
	//提供一个共有的静态方法,返回实例对象，加入了同步处理的方法
	public static synchronized Singleton1 getInstance() {
		if(instance==null) {
			instance= new Singleton1();
		}
		return  instance;
	}
}