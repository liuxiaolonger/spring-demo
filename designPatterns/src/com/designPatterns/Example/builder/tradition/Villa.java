package com.designPatterns.Example.builder.tradition;
/**
 * 别墅
 * @author Admin
 *
 */
public class Villa extends  AbsHouses{

	@Override
	void foundation() {
	System.out.println("给别墅打地基");
		
	}

	@Override
	void masonryWall() {
		System.out.println("给别墅砌墙");
		
	}

	@Override
	void capping() {
		System.out.println("给别墅封顶");
		
	}

}
