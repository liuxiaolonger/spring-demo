package com.designPatterns.Example.Adapter.objectType;

public class Client {
	public static void main(String[] args) {
         Phone b=new Phone();
         b.charge(new VoltageAdapter(new Voltage()));
	}
}
