package com.designPatterns.Example.Adapter.interfaceType;

public class Client {
	public static void main(String[] args) {
         Phone b=new Phone();
         b.charge(new VoltageAdapter(new Voltage()));
	}
}
