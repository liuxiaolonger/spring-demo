package com.designPatterns.Example.singleCase.enums;

/**
 * @author Admin
    *         线程安全 懒加载
 */

public class Statics {
	public static void main(String[] args) {
		Singleton  s=Singleton.INSTANCE;
		Singleton  ss=Singleton.INSTANCE;
		System.out.println(s == ss);
		s.sayOk();
		System.out.println(s.hashCode());
		System.out.println(ss.hashCode());
	}
}
/**
 * @author Admin
 *
 */
enum  Singleton{
      INSTANCE;
     public void sayOk() {
    	 System.out.println("ok");
     }
}