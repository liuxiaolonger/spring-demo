package com.designPatterns.Example.builder.optimization;
/**
 * 指挥者
 * @author Admin
 *
 */
public class HourseDirector {
	private HousesBuilder housesBuilder;
	public HourseDirector(HousesBuilder housesBuilder) {
		this.housesBuilder = housesBuilder;
	}
	public HousesBuilder getHousesBuilder() {
		return housesBuilder;
	}
	public void setHousesBuilder(HousesBuilder housesBuilder) {
		this.housesBuilder = housesBuilder;
	}
	//具体建造者
	public Hourse constructHouse() {
		housesBuilder.foundation();
		housesBuilder.masonryWall();
		housesBuilder.capping();
		return housesBuilder.build();
	}
}
