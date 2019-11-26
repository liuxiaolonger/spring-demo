package com.etoc.service.area.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysQueryService;
import com.etoc.model.AreaInfo;
import com.etoc.service.area.AbsAreaService;
import com.etoc.service.area.vo.Area;
import com.etoc.service.area.vo.AreaInfoResource;

/**
 * 
 * 根据地区主键查询地区
 * 
 * @author longlong
 * @version [版本号, 2019年1月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryArea extends AbsAreaService implements SysQueryService<AreaInfoResource> {
	private String areaId;

	private String[] fields;

	private AreaInfo areaInfo = new AreaInfo();

	private AreaInfo areaInfoCity = new AreaInfo();

	private AreaInfo areaInfoProvince = new AreaInfo();

	protected QueryArea addAreaId(String areaId) {
		this.areaId = areaId;
		return this;
	}

	protected QueryArea addFields(String[] fields) {
		this.fields = fields;
		return this;
	}

	@Override
	public void execute() throws Exception {
		AreaInfo areaInfo = areaInfoMapper.selectByPrimaryKey(this.areaId);
		if (areaInfo != null)
			this.areaInfo = areaInfo;

		// 查询市地区信息
		String cityStr = areaId.substring(0, 4);
		String cityId = cityStr + "00";
		AreaInfo areaInfoCity = areaInfoMapper.selectByPrimaryKey(cityId);
		if (areaInfoCity != null)
			this.areaInfoCity = areaInfoCity;

		// 查询省地区信息
		String provinceStr = areaId.substring(0, 2);
		String provinceId = provinceStr + "0000";
		AreaInfo areaInfoProvince = areaInfoMapper.selectByPrimaryKey(provinceId);
		if (areaInfoProvince != null)
			this.areaInfoProvince = areaInfoProvince;
	}

	@Override
	public AreaInfoResource resultObj() throws Exception {
		Area areaCity = (Area) transferObjectFields(areaInfoCity, Area.class, fields);
		Area areaProvince = (Area) transferObjectFields(areaInfoProvince, Area.class, fields);
		Map<String, Object> objMap = new HashMap<>();
		objMap.put("areaCity", areaCity);
		objMap.put("areaProvince", areaProvince);
		AreaInfoResource area =  transferObjectFields(areaInfo, AreaInfoResource.class, fields, objMap);

		return area;
	}

}
