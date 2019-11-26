package com.designPatterns.Example.factory.ImprovementFactory;
//客户端，发出订购订单
public class PizzaStore {
     public static void main(String[] args) {
		new OrderPizza(new SimpleFactory());
		System.out.println("退出生产");
	}
}
