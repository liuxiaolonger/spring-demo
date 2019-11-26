package com.designPatterns.Example.Adapter.classType;

public class VoltageAdapter extends Voltage implements InsertingPlate {
	/**
	 * 降压操作
	 */
	@Override
	public int output() {
		int out=output220();
		int outo=out/44;
		System.out.println("输出电压为5v");
		return outo;
	}
}
