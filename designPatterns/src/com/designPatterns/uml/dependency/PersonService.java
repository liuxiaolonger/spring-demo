package com.designPatterns.uml.dependency;

public class PersonService {
	private PersonDao dao;

	public void sava(Person person) {

	}

	public IdCard getIdCard(Integer personId) {
		return new IdCard();
	}

	public void modify(Person person) {
       Dependency d=new Dependency();
	}
}
