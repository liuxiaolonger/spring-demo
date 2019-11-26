package com.designPatterns.Example.factory.AbstractFactory;
/**
 * 静态工厂
 * @author Admin
 *
 */
public class PekingFactory implements AbsFactory {

	@Override
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
