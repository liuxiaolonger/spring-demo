package com.longer.service.area;

import com.etoc.constant.DataType;
import com.longer.base.SysQueryService;

public interface AreaService
{

	
    AreaService addAreaId(String areaId);
    
    AreaService addAreaIds(String[] areaIds);
    
    AreaService addAreaType(Integer areaType);
   
    AreaService addAreaName(String areaName);

    /**
     * 查询地区信息 
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryArea(String[] fields) throws Exception;;
    
    
    /**
     * 查询地区集合
     * <功能详细描述>
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @param fields
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    SysQueryService<?> queryArea(Integer pageNum, Integer pageSize, DataType dataType, String[] fields) throws Exception;;
}
