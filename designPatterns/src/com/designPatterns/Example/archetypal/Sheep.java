package com.designPatterns.Example.archetypal;

public class Sheep implements Cloneable {
     private String name;
     
     private Sheep friend;

	public Sheep getFriend() {
		return friend;
	}
	public void setFriend(Sheep friend) {
		this.friend = friend;
	}
	public Sheep(String name, Integer age, String color) {
		super();
		this.name = name;
		this.age = age;
		this.color = color;
	}

	@Override
	public String toString() {
		return "Sheep [name=" + name + ", friend=" + friend + ", age=" + age + ", color=" + color + "]";
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
	protected Object clone(){
		Sheep sheep=null;
		try {
			sheep=(Sheep) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return sheep;
	}
}
