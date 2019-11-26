package com.longer.service.area.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.util.PageInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.longer.base.impl.SysQueryPageService;
import com.longer.dao.model.AreaInfo;
import com.longer.service.area.AbsAreaService;
import com.longer.service.area.vo.AreaInfoResource;

/**
 * 
 * 分页功能
 * 
 * @author longlong
 * @version [版本号, 2019年1月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryPageArea extends AbsAreaService implements SysQueryPageService<AreaInfoResource> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Integer pageNum;

	private Integer pageSize;

	private String areaId;

	private Integer areaType;

	private String areaName;

	private String[] fields;

	private List<AreaInfo> areaInfos = new ArrayList<>();

	protected QueryPageArea addAreaId(String areaId) {
		this.areaId = areaId;
		return this;
	}

	protected QueryPageArea addAreaType(Integer areaType) {
		this.areaType = areaType;
		return this;
	}

	protected QueryPageArea addAreaName(String areaName) {
		this.areaName = areaName;
		return this;
	}

	protected QueryPageArea setQueryParams(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		return this;
	}

	protected QueryPageArea setQueryParams(Integer pageNum, Integer pageSize, String[] fields) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.fields = fields;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (areaType != null) {
			map.put("areaType", areaType);
		}
		if (StringUtil.isNotEmpty(areaId)) {
			map.put("areaId", areaId);
		}
		if (StringUtil.isNotEmpty(areaName)) {
			map.put("areaName", areaName);
		}
		PageHelper.startPage(pageNum, pageSize);
		areaInfos = areaInfoMapper.selectArea(map);
		logger.info("数据库查询地区集合结果areaInfos= {}", areaInfos.toString());
	}

	@Override
	public PageInfo<AreaInfoResource> resultObj() throws Exception {
		Page<AreaInfoResource> lists = new Page<>(pageNum, pageSize);
		lists.setTotal(((Page<AreaInfo>) areaInfos).getTotal());
		for (AreaInfo area : areaInfos) {
			AreaInfoResource are = transferObjectFields(area, AreaInfoResource.class, fields);
			lists.add(are);
		}
		PageInfo<AreaInfoResource> areaInfoPage = new PageInfo<>(lists,AreaInfoResource.class);
		return areaInfoPage;
	}

}
