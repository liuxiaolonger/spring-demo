package com.longer.base.impl;



import com.etoc.vo.TreeNode;
import com.longer.base.SysQueryService;

public interface SysQueryTreeService extends SysQueryService<TreeNode> {
	
	TreeNode resultObj() throws Exception;
	
}
