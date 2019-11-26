package com.designPatterns.Example.builder.optimization;

public class Client {
    public static void main(String[] args) {
    	Villa villa=new Villa();
    	HourseDirector hourseDirector=new HourseDirector(villa);
    	hourseDirector.constructHouse();
	}
}
