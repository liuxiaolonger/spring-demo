package com.designPatterns.Example.archetypal.deep;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Sheep implements Serializable, Cloneable {
     private String name;
     
     private SheepDemo friend;
 
	
	public SheepDemo getFriend() {
		return friend;
	}

	public void setFriend(SheepDemo friend) {
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
	/**
	 * 浅拷贝
	 */
	@Override
	protected Object clone(){
		Sheep sheep=null;
		try {
			sheep=(Sheep) super.clone();
			sheep.friend=(SheepDemo) friend.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return sheep;
	}
	/**
	 * 深拷贝
	 */
	public  Object deepClone(){
		//创建流对象
		Sheep sheep=null;
		ByteArrayOutputStream bos=null;
		ObjectOutputStream  oos=null;
		ByteArrayInputStream bis=null;
		ObjectInputStream  ois=null;
		try {
			//序列化
			bos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(bos);
			oos.writeObject(this);
			//反序列化
			bis=new ByteArrayInputStream(bos.toByteArray());
			ois=new ObjectInputStream(bis);
			sheep=	(Sheep) ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally {
			try {
				bos.close();
				bis.close();
				oos.close();
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		return sheep;
	}


}
