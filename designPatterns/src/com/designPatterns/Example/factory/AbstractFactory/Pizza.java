package com.designPatterns.Example.factory.AbstractFactory;

/**
 * 披萨类的抽象类
 * 
 * @author Admin
 *
 */
public abstract class Pizza {
	//名字
	protected String name;
    //不同的披萨，不同的材料
	public abstract void prepare();
    //烘烤
	public void bake() {
		System.out.println(name+"bakeing");
	};
    //切割
	public void cut() {
		System.out.println(name+"cutting");
	};
	//包装
	public void box() {
		System.out.println(name+"boxing");
	};
    //设置名称
	public void setName(String name) {
		this.name = name;
	}
}
