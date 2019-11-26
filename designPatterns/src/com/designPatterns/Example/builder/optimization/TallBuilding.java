package com.designPatterns.Example.builder.optimization;
/**
 * 高楼
 * @author Admin
 *
 */
public class TallBuilding extends  HousesBuilder {

	@Override
	void foundation() {
		System.out.println("给高楼打地基");
		
	}

	@Override
	void masonryWall() {
		System.out.println("给高楼砌墙");
		
	}

	@Override
	void capping() {
		System.out.println("给高楼封顶");
	}

}
