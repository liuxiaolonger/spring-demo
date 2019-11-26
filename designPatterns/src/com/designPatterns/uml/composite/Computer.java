package com.designPatterns.uml.composite;

public class Computer {
   public KeyBord  perso;
   public Mouse mouser;
   public Cpu cup;//组合就会说不可分割
public void setCup(Cpu cup) {
	this.cup = cup;
}
}
