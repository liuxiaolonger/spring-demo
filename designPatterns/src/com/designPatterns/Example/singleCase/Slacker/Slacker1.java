package com.designPatterns.Example.singleCase.Slacker;

public class Slacker1 {

	public static void main(String[] args) {
		Singleton  s=Singleton.getInstance();
		Singleton  ss=Singleton.getInstance();
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
class Singleton{
	//构造器私有化，外部不能new
	private Singleton() {}
	//在本类内部创建对象实例
	private final static Singleton instance=new Singleton();
	//提供一个共有的静态方法,返回实例对象
	public static Singleton getInstance() {
		return  instance;
	}
}