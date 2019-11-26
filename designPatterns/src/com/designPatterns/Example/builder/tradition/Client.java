package com.designPatterns.Example.builder.tradition;

public class Client {
    public static void main(String[] args) {
		AbsHouses a=new Villa();
		a.build();
	}
}
