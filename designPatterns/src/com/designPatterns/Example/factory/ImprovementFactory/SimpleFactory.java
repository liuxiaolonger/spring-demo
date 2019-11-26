package com.designPatterns.Example.factory.ImprovementFactory;
/**
 * 简单工厂
 * @author Admin
 *
 */
public class SimpleFactory {
	//根据需要，返回pizaa披萨
	public Pizza createPizza(String orderType) {
		Pizza pizza = null;
		if (orderType.equals("California")) {
			pizza = new CaliforniaPizza();
			pizza.setName("加利福尼亚");
		} else if (orderType.equals("Chicago")) {
			pizza = new ChicagoPizza();
			pizza.setName("芝加哥");
		}
        return  pizza;
	}

}
