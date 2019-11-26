package com.designPatterns.Example.archetypal.deep;

import java.io.Serializable;

public class SheepDemo implements Serializable, Cloneable {
     private String name;
     
	public SheepDemo(String name, Integer age, String color) {
		super();
		this.name = name;
		this.age = age;
		this.color = color;
	}

	private Integer age;
     public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	private String color;
	@Override
	public String toString() {
		return "SheepDemo [name=" + name + ", age=" + age + ", color=" + color + "]";
	}
	@Override
	protected Object clone(){
		SheepDemo sheep=null;
		try {
			sheep=(SheepDemo) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return sheep;
	}
}
