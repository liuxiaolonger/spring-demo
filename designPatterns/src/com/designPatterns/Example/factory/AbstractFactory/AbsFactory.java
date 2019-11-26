package com.designPatterns.Example.factory.AbstractFactory;
/**
 * 抽象工厂（接口）
 * @author Admin
 *
 */
public interface AbsFactory {
	//根据需要，返回pizaa披萨
	public Pizza createPizza(String orderType);
}
