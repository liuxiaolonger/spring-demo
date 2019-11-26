package com.designPatterns.Example.singleCase.statics;

/**
 * @author Admin
    *         线程安全 懒加载
 */

public class Statics {
	public static void main(String[] args) {
		Singleton  s=Singleton.getInstance();
		Singleton  ss=Singleton.getInstance();
		System.out.println(s == ss);
		System.out.println(s.hashCode());
		System.out.println(ss.hashCode());
	}
}
/**
 * @author Admin
 *
 */
class Singleton{
	//构造器私有化，外部不能new
	private Singleton() {}
	//在本类内部创建对象实例
	private  static class Singletonnstance{
		private static final Singleton INSTANCE=new Singleton();
	}
	//提供一个共有的静态方法,返回实例对象，在线程1进入判断而还没有来的及创建对象的时候，线程2进来还是会重新创建一个对象、
	public static Singleton getInstance() {
		 return Singletonnstance.INSTANCE;
	}
}