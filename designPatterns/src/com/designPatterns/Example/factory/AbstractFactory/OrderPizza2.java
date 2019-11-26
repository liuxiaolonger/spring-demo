package com.designPatterns.Example.factory.AbstractFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
public class OrderPizza2 {
	AbsFactory simpleFactory;

	public void setFactory(AbsFactory absFactory) {
		this.simpleFactory = new PekingFactory();
		do {

			Pizza p = this.simpleFactory.createPizza(getType());
			if (p != null) {
				p.prepare();
				p.bake();
				p.cut();
				p.box();
			} else {
				System.out.println("订购失败");
				break;
			}

		} while (true);
	}
	
	// 写一个方法
	private String getType() {
		try {
			BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("请输入披萨：");
			String str = strin.readLine();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
