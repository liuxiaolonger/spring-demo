package com.designPatterns.Example.Adapter.objectType;

public class VoltageAdapter  implements InsertingPlate {
	private  Voltage v;
	public VoltageAdapter(Voltage v) {
		super();
		this.v = v;
	}
	/**
	 * 降压操作
	 */
	@Override
	public int output() {
	  int out=	v.output220();
	 System.out.println("降压操作对象适配器，输出5v");
		return out/44;
	}
}
