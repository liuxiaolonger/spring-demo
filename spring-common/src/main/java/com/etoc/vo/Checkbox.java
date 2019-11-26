package com.etoc.vo;

import java.io.Serializable;

/**
 * 
 * 下拉框实体类
 * 
 * @author 李子豪
 * @version [版本号, 2018年6月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Checkbox implements Serializable {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2229101746974856898L;
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Checkbox(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Checkbox() {
		super();
	}

	@Override
	public String toString() {
		return "Checkbox [id=" + id + ", name=" + name + "]";
	}

}
