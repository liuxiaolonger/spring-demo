package com.designPatterns.Example.archetypal.deep;

/**
 * xml文件中读取类，用的就是原型模式
 * @author Admin
 *
 */
public class Client {
 public static void main(String[] args) {
	Sheep e=new Sheep("上海养", 10, "red");
	//地址引用，指向的是同一个对象，修改其中一个的属性，会改变所有引用的对象
    e.setFriend(new SheepDemo("北京养", 10, "red"));
	Sheep e1=new Sheep(e.getName(), e.getAge(), e.getColor());
	Sheep e2=new Sheep(e.getName(), e.getAge(), e.getColor());
	Sheep e3=new Sheep(e.getName(), e.getAge(), e.getColor());
	System.out.println(e);
	System.out.println(e1);
	System.out.println(e2);
	System.out.println(e3);
	
	Sheep e11=(Sheep) e.deepClone();
	Sheep e112=(Sheep) e.deepClone();
	Sheep e113=(Sheep) e.deepClone();
	Sheep e114=(Sheep) e.deepClone();
	System.out.println(e11.getFriend().hashCode());
	System.out.println(e112.getFriend().hashCode());
	System.out.println(e114.getFriend().hashCode());
	System.out.println(e113.getFriend().hashCode());
}
}
