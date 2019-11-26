package com.designPatterns.Example.Adapter.interfaceType;

public class Phone {
	//充电
  public void charge(InsertingPlate v) {
	  if(v.output()==5) {
		  System.err.println("给手机充电5v");
	  }
	  
  }
}
