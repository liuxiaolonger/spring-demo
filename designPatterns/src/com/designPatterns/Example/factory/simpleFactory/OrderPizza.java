package com.designPatterns.Example.factory.simpleFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class OrderPizza {	
	
    public OrderPizza() {
    	Pizza pizza=null;
    	String orderType;//pizza的类型
    	do {
    		orderType=getType();
    		if(orderType.equals("California")) {
    			pizza= new CaliforniaPizza();
    			pizza.setName("加利福尼亚");
    		}else if(orderType.equals("Chicago")) {
    			pizza= new ChicagoPizza();
    			pizza.setName("芝加哥");
    		}else {
    			break;
    		}
    		pizza.prepare();
        	pizza.bake();
        	pizza.cut();
        	pizza.box();
    	}while(true);
    
    }
    
    //写一个方法
    private  String getType() {
    	try {
			BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("请输入披萨：");
			String str=strin.readLine();
			return str;
		} catch (Exception e) {
		e.printStackTrace();
		return "";
		}
    }
}
