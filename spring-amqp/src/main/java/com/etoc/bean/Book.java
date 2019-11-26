package com.etoc.bean;

/**
 * Created by Admin on 2019/4/20.
 */
public class Book {
	public Book() {
		super();
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", phone=" + phone + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String bookId;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	private String name;
	private String phone;
	private Integer num;

	public Book(String bookId, String name, String phone, Integer num) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.phone = phone;
		this.num = num;
	}

}
