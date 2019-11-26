package com.etoc.bean;

public class User {
	private String userName;
	private String addr;
	private Integer age;
	private String pwd;

	public User() {
		super();
	}

	public User(String userName) {
		super();
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", addr=" + addr + ", age=" + age + ", pwd=" + pwd + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
