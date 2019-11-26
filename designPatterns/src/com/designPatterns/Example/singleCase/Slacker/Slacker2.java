package com.designPatterns.Example.singleCase.Slacker;

public class Slacker2 {

	public static void main(String[] args) {
		Singleton1  s=Singleton1.getInstance();
		Singleton1 ss=Singleton1.getInstance();
		System.out.println(s==ss);
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
	private final static Singleton1 instance;
	//在本类静态代码块
	static {
		instance= new Singleton1();    
	}
	//提供一个共有的静态方法,返回实例对象
	public static Singleton1 getInstance() {
		return  instance;
	}
}