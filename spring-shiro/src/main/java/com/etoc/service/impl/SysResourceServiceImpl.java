package com.etoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoc.constant.CommonConst;
import com.etoc.mapper.SysResourceMapper;
import com.etoc.model.SysResource;
import com.etoc.service.SysResourceService;
import com.etoc.vo.Tree;
import com.etoc.vo.TreeNode;

@Service
public class SysResourceServiceImpl implements SysResourceService {

	@Autowired
	SysResourceMapper sysResourceMapper;

	@Override
	public TreeNode getResourcesTree(SysResource record) {
		Tree tree = new Tree();
		// 设置状态为0 可用
//		record.setAvailable(CommonConst.system.STATUS_ZERO_Integer);
		// 设置平台类型. 1 表示代理商管理平台
		record.setSystemType(CommonConst.system.STATUS_ONE);
		List<SysResource> sysResources = sysResourceMapper.selectResourcesByQuery(record);
		// 获取所有不可用的资源
//		List<SysResource> lists = sysResourceMapper.getNotResources(record);
//		// 保存所有不可用资源以及其子资源
//		List<String> codes = new ArrayList<>();
//		for (SysResource res : lists) {
//			// 获取所有不可用资源下的子资源
//			List<SysResource> list = sysResourceMapper.getNotSonResources(res.getCode());
//			codes.add(res.getCode());
//			for (SysResource sys : list) {
//				codes.add(sys.getCode());
//			}
//		}
//		// 获取所有可用资源
//		List<SysResource> sysResources = sysResourceMapper.selectResourcesAll(codes);
		if (CollectionUtils.isNotEmpty(sysResources)) {
			List<TreeNode> treeList = new ArrayList<TreeNode>();
			for (SysResource item : sysResources) {
				TreeNode node = new TreeNode(item.getCode(), item.getName(), item.getParentCode(), item.getPriority());
				node.putExtData("id", item.getId());
				treeList.add(node);
			}
			tree.setTempNodeList(treeList);
			tree.generateTree();
		}
		return tree.getRoot() == null ? new TreeNode() : tree.getRoot();
	}

	@Override
	public SysResource selectByPrimaryKey(String resourceId) {
		SysResource sysResource = sysResourceMapper.selectByPrimaryKey(resourceId);
		return sysResource;
	}

	@Override
	public List<SysResource> getSysResources() {
		List<SysResource> sysResources = sysResourceMapper.selectSysResources();
		return sysResources;
	}

	@Override
	public List<SysResource> listResourcesByUserId(String userId) {
		List<SysResource> resources = sysResourceMapper.selectResourcesByUserId(userId);
		return resources;
	}

}