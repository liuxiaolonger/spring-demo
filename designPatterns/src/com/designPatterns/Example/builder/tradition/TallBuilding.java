package com.designPatterns.Example.builder.tradition;
/**
 * 高楼
 * @author Admin
 *
 */
public class TallBuilding extends  AbsHouses {

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
