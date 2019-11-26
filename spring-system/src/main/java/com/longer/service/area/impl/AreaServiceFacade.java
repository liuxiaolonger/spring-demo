package com.longer.service.area.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.constant.DataType;
import com.longer.annotation.ReadOnlys;
import com.longer.base.SysQueryService;
import com.longer.service.area.AbsAreaService;
import com.longer.service.area.AreaService;

/**
 * 
 * @author Admin
 *
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AreaServiceFacade extends AbsAreaService implements AreaService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String areaId;// 区域主键ID

	private Integer areaType;// 区域类型

	private String areaName;// 区域名称
	
	private String[] areaIds;//地域主键ID数组
	
    @Override
    public AreaService addAreaIds(String[] areaIds)
    {
        this.areaIds=areaIds;
        return this;
    }

	@Override
	public AreaService addAreaId(String areaId) {
		this.areaId = areaId;
		return this;
	}

	@Override
	public AreaService addAreaType(Integer areaType) {
		this.areaType = areaType;
		return this;
	}

	@Override
	public AreaService addAreaName(String areaName) {
		this.areaName = areaName;
		return this;}
	

	@Override
	@ReadOnlys
	public SysQueryService<?> queryArea(String[] fields) throws Exception {
		logger.info("查询单个地区信息！");
		SysQueryService<?> queryPageService = context.getBean(QueryArea.class)
				.addAreaId(areaId).addFields(fields);

		logger.info("执行器开始查询！");
		queryPageService.execute();
		return queryPageService;
	}
	

	@Override
	@ReadOnlys
	public SysQueryService<?> queryArea(Integer pageNum, Integer pageSize, DataType dataType, String[] fields) throws Exception {
		SysQueryService<?> sysQueryService = null;
		if (dataType == null) {// 设置默认分页查询
			dataType = DataType.Page;
		}
		if (pageNum == null || pageSize == null) {// 设置默认分页查询的当前页和每页数量
			pageNum = 1;
			pageSize = 10;
		}
		if (dataType == DataType.Page) {
			logger.info("分页查询地区信息集合！");
			sysQueryService = context.getBean(QueryPageArea.class)
					.addAreaId(areaId).addAreaName(areaName).addAreaType(areaType)
					.setQueryParams(pageNum, pageSize, fields);

		} else if (dataType == DataType.List) {
			// 当areaId为null 	  areaType为null    时查询所有地区
			// 当areaId为null  	  areaType不为null  时按地区类型查询
			// 当areaId不为null    areaType不为null	时根据父级id及类型查询其子级地域信息 
			logger.info("查询地区信息List集合！");
			sysQueryService = context.getBean(QueryListArea.class)
					.addAreaId(areaId).addAreaType(areaType)
					.addFields(fields).addAreaIds(areaIds);

		} else if (dataType == DataType.Tree) {
			logger.info("查询地区树型菜单！");
			sysQueryService = context.getBean(QueryAreaTree.class);

		}
		logger.info("执行器开始执行！");
		sysQueryService.execute();
		return sysQueryService;
	}



}
