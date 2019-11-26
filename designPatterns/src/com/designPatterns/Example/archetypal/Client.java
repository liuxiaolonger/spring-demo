package com.designPatterns.Example.archetypal;

/**
 * xml文件中读取类，用的就是原型模式
 * @author Admin
 *
 */
public class Client {
 public static void main(String[] args) {
	Sheep e=new Sheep("上海养", 10, "red");
	//地址引用，指向的是同一个对象，修改其中一个的属性，会改变所有引用的对象
    e.setFriend(new Sheep("上海养", 10, "red"));
	Sheep e1=new Sheep(e.getName(), e.getAge(), e.getColor());
	Sheep e2=new Sheep(e.getName(), e.getAge(), e.getColor());
	Sheep e3=new Sheep(e.getName(), e.getAge(), e.getColor());
	System.out.println(e);
	System.out.println(e1);
	System.out.println(e2);
	System.out.println(e3);
	
	Sheep e11=(Sheep) e.clone();
	Sheep e112=(Sheep) e.clone();
	Sheep e113=(Sheep) e.clone();
	Sheep e114=(Sheep) e.clone();
	System.out.println(e11);
	System.out.println(e112);
	System.out.println(e114);
	System.out.println(e113);
}
}
