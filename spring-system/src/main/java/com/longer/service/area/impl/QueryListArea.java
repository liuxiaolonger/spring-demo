package com.longer.service.area.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.etoc.util.StringUtil;
import com.longer.annotation.ReadOnlys;
import com.longer.annotation.WriteOnlys;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.AreaInfo;
import com.longer.service.area.AbsAreaService;
import com.longer.service.area.vo.Area;

/**
 * 
 * 查询所有地区
 * 
 * @author longlong
 * @version [版本号, 2019年1月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListArea extends AbsAreaService implements SysQueryListService<Area> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String areaId;

	private Integer areaType;

	private String[] fields;
	
	private String[] areaIds;//地域主键ID集合

	private List<AreaInfo> areaInfos = new ArrayList<>();

	protected QueryListArea addAreaId(String areaId) {
		this.areaId = areaId;
		return this;
	}

	protected QueryListArea addAreaType(Integer areaType) {
		this.areaType = areaType;
		return this;
	}

	protected QueryListArea addFields(String[] fields) {
		this.fields = fields;
		return this;
	}

	protected QueryListArea addAreaIds(String[] areaIds) {
		this.areaIds = areaIds;
		return this;
	}
	
	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isNotEmpty(areaId) && areaType != null) {
			switch (areaType) {
			case 0:
				areaId = "__0000";
				break;
			case 1:
				areaId = areaId.substring(0, 2) + "__00";
				break;
			case 2:
				areaId = areaId.substring(0, 4) + "__";
				break;
			case 3:
				break;
			}
			map.put("areaId", areaId);
			map.put("areaTypeNot", areaType);
		}
		if (StringUtil.isEmpty(areaId) && areaType != null) {
			map.put("areaType", areaType);
		}
		
		if(areaIds!=null&&areaIds.length>0) {
			List<String> areas = Arrays.asList(areaIds);
			map.put("areaIds", areas);
		}
		
		areaInfos = areaInfoMapper.selectByAreaParms(map);
		logger.info("数据库查询地区集合结果areaInfos= {}", areaInfos.toString());
	}

	@Override
	public List<Area> resultObj() throws Exception {
		List<Area> lists = new ArrayList<>();
		this.areaInfos.forEach(area -> {
			try {
				Area are = transferObjectFields(area, Area.class, fields);
				lists.add(are);
			} catch (Exception e) {
				logger.info("数据库对象转化为VO对象失败e:", e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return lists;
	}

}
