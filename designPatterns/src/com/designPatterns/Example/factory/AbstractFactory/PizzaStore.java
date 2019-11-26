package com.designPatterns.Example.factory.AbstractFactory;
//客户端，发出订购订单
public class PizzaStore {
     public static void main(String[] args) {
	//	new OrderPizza().setFactory(new PekingFactory());
		new OrderPizza().setFactory(new NewYoFactory2());
		System.out.println("退出生产");
	}
}
