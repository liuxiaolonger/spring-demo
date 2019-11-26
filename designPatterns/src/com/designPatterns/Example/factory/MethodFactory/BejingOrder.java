package com.designPatterns.Example.factory.MethodFactory;



public class BejingOrder extends OrderPizza{
	@Override
	Pizza createPizza(String getType) {
	     Pizza pizza=null;
	     if(getType.equals("California")) {
 			pizza= new CaliforniaPizza();
 			pizza.setName("加利福尼亚");
 		}else if(getType.equals("Chicago")) {
 			pizza= new ChicagoPizza();
 			pizza.setName("芝加哥");
 		}
 		pizza.prepare();
     	pizza.bake();
     	pizza.cut();
     	pizza.box();
		return pizza;
	}  
}
