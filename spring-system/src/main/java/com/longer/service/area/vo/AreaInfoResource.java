package com.longer.service.area.vo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.etoc.constant.RelConst;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;
import com.longer.service.area.AreaController;

public class AreaInfoResource extends BaseResource {

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

		add(linkTo(methodOn(AreaController.class).get(areaId, null)).withRel(RelConst.REL_SELF));
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
		return "AreaInfoResource [areaId=" + this.areaId + ", areaName=" + this.areaName + ", areaType=" + this.areaType + ", countriesId=" + this.countriesId + "]";
	}

	@Override
	public void setLikes(PageInfo<?> page)
	{
		page.addLink(linkTo(methodOn(AreaController.class).searchArea(null, null, null, null, null, null, null, null)).withRel(RelConst.REL_SEARCH));
	}

}