package com.etoc.service.area;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.base.SysQueryService;
import com.etoc.constant.DataType;
import com.etoc.exception.ChannelException;
import com.etoc.service.ContextHolder;
import com.etoc.service.area.impl.AreaServiceFacade;

/**
 * 
 * 地区信息
 * 
 * @author 朱鹏
 * @version [版本号, 2019年1月2日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@RequestMapping("/areas")
public class AreaController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ContextHolder context;

	/**
	 * 1.查询地区信息集合 DataType为Page 参数 pageNum pageSize 
	 * 2.获取地区信息树 DataType为Tree 参数 null
	 * 3.父级id及类型查询其子级地域信息 DataType为List 参数 areaId areaType 
	 * 4.根据区域类型:查找地区信息DataType为List 参数 areaType 
	 * 5.获取所有地区信息 DataType为List 参数 null
	 * 6.根据地区ids查询地区集合    DataType为List  参数  areaIds
	 * @param pageNum  第几页
	 * @param pageSize 页大小
	 * @param areaId   地区id
	 * @param areaName 地区名称
	 * @param areaType 地区类型
	 * @param DataType List、Page、Tree、Map 返回结果集类型
	 * @param          fields[] 返回字段
	 * 
	 * @return json(List/Page/Tree/Map)
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> searchArea(Integer pageNum, Integer pageSize, DataType dataType, String[] fields, 
			String areaId, String areaName, Integer areaType,String[] areaIds) {
		logger.info("查询地区信息集合入参为：pageNum = {},  pageSize = {},  dataType = {}, fields = {}, " + "areaId = {}, areaName = {} , areaType = {}",
				pageNum, pageSize, dataType, Arrays.toString(fields), areaId, areaName,areaType);
				
		try {
			AreaService areaServiceFacade = context.getBean(AreaServiceFacade.class)
					.addAreaId(areaId).addAreaType(areaType)
					.addAreaName(areaName).addAreaIds(areaIds);

			SysQueryService<?> sysQueryService = areaServiceFacade.queryArea(pageNum, pageSize, dataType, fields);
			Object obj = sysQueryService.resultObj();

			logger.info("查询地区信息集合结果：{}", obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("地区信息集合失败!!", e);
			throw new ChannelException("地区信息集合失败!", e);
		}
	}

	/**
	 * 1.根据id查询地区信息 
	 * 2.根据地域id查询所属省份及所属市
	 * 
	 * @param areaId 地球主键
	 * @param fields 返回字段
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping(value = "/{id}/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> get(@PathVariable(name = "id", required = true) String areaId, String[] fields) {
		logger.info("查询单条地区信息的 areaId = {} , fields = {}", areaId, fields);
		try {
			AreaService areaServiceFacade = context.getBean(AreaServiceFacade.class).addAreaId(areaId);
			SysQueryService<?> sysQueryService = areaServiceFacade.queryArea(fields);

			Object obj = sysQueryService.resultObj();
			logger.info("查询单条地区信息的结果为Area = {}", obj);
			return new ResponseEntity<Object>(obj, HttpStatus.OK);
		} catch (ChannelException channelException) {
			throw channelException;
		} catch (Exception e) {
			logger.error("查询单条地区信息失败!!", e);
			throw new ChannelException("查询单条地区信息失败", e);
		}
	}


}
