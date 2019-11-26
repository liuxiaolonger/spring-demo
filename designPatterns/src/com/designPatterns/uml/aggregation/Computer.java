package com.designPatterns.uml.aggregation;

public class Computer {
   public KeyBord  person;
   public void setPerson(KeyBord person) {
	this.person = person;
}
public void setMouser(Mouse mouser) {
	this.mouser = mouser;
}
public Mouse mouser;
}
