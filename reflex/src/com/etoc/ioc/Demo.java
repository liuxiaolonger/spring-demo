package com.etoc.ioc;

import com.etoc.bean.User;

public class Demo {

	public static void main(String[] args) throws Exception {
		ClassPathXml xml = new ClassPathXml("newfile.xml");
		User user = (User) xml.getBean("user1");
		System.out.println(user);
	}
}
