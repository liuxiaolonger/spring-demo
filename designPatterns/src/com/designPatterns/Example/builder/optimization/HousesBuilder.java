package com.designPatterns.Example.builder.optimization;

/**
 * 抽象建造者
 * 
 * @author Admin
 *
 */
public abstract class HousesBuilder {
	protected Hourse hourse=new Hourse();
	/**
	 * 打地基
	 */
	abstract void foundation();

	/**
	 * 砌墙
	 */
	abstract void masonryWall();

	/**
	 * 封顶
	 */
	abstract void capping();

	/**
	 * 完成建房子的动作
	 */
	public Hourse build() {
		return  hourse;
	}
}
