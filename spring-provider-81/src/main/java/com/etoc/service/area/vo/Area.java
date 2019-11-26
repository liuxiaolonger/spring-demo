package com.etoc.service.area.vo;

/**
 * 
 * 地区vo
 * @author  longlong
 * @version  [版本号, 2019年1月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Area {
	
	// 地区id
	private String areaId;
	// 地区名称
	private String areaName;
	// 地区类型
	private Integer areaType;
	// 外键，国家信息编号
	private String countriesId;
	// 所属市
	private Area areaCity;
	// 所属省份
	private Area areaProvince;
 
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getAreaType() {
		return this.areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public String getCountriesId() {
		return this.countriesId;
	}

	public void setCountriesId(String countriesId) {
		this.countriesId = countriesId;
	}

	public Area getAreaCity() {
		return this.areaCity;
	}

	public void setAreaCity(Area areaCity) {
		this.areaCity = areaCity;
	}

	public Area getAreaProvince() {
		return this.areaProvince;
	}

	public void setAreaProvince(Area areaProvince) {
		this.areaProvince = areaProvince;
	}

	
	@Override
	public String toString() {
		return "Area [areaId=" + this.areaId + ", areaName=" + this.areaName + ", areaType=" + this.areaType + ", countriesId=" + this.countriesId + ", areaCity=" + this.areaCity + ", areaProvince="
				+ this.areaProvince + "]";
	}

}
