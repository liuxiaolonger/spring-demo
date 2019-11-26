package com.etoc.base.impl;

import com.etoc.base.SysQueryService;
import com.etoc.vo.TreeNode;

public interface SysQueryTreeService extends SysQueryService<TreeNode> {
	
	TreeNode resultObj() throws Exception;
	
}
