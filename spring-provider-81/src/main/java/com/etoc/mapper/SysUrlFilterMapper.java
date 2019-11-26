package com.etoc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoc.model.SysUrlFilter;



@Mapper
public interface SysUrlFilterMapper {
    
	//分页查询url权限管理
    List<SysUrlFilter> selectSysUrl(Map<String,Object> query);
    
    //查询所有url权限信息
    List<SysUrlFilter> selectAllSysUrl();
    
    //添加url信息
    int insertSysUrl(SysUrlFilter record);
    
    //根据id查询
  	SysUrlFilter selectBySysUrlId(@Param("urlId")String urlId);
    
    //保存修改后的url信息
  	int updateSysUrl(SysUrlFilter query);
    
    //删除单条或多条信息
  	int deleteSysUrl(@Param("urlId")  String urlId);
}