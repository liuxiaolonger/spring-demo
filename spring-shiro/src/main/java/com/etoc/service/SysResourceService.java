package com.etoc.service;

import java.util.List;

import com.etoc.model.SysResource;
import com.etoc.vo.TreeNode;



public interface SysResourceService {
	// 根据系统类型查询资源树结构
	TreeNode getResourcesTree(SysResource record);

	// 根据资源id进行查询
	SysResource selectByPrimaryKey(String resourceId);

	// 查询全部资源
	List<SysResource> getSysResources();

	//通过用户ID查询资源信息
	List<SysResource> listResourcesByUserId(String userId);
	

}
