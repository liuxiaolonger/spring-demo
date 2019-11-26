package com.designPatterns.Example.builder.tradition;

/**
 * 房子的抽象类
 * 
 * @author Admin
 *
 */
public abstract class AbsHouses {
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
	public void build() {
		foundation();
		masonryWall();
		capping();
	}
}
