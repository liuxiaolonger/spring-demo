/*
 * 文 件 名:  QueryAreaTree.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2019年1月16日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.area.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.vo.Tree;
import com.etoc.vo.TreeNode;
import com.longer.base.impl.SysQueryTreeService;
import com.longer.dao.model.AreaInfo;
import com.longer.service.area.AbsAreaService;

/**
 * 查询地区树
 * 
 * @author longlong
 * @version [版本号, 2019年1月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryAreaTree extends AbsAreaService implements SysQueryTreeService{

	private List<AreaInfo> areaInfos = new ArrayList<>();

	/** {@inheritDoc} */

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		areaInfos = areaInfoMapper.selectByAreaParms(map);

	}

	/** {@inheritDoc} */

	@Override
	public TreeNode resultObj() throws Exception {
		Tree tree = new Tree();
		if (CollectionUtils.isNotEmpty(this.areaInfos)) {
			List<TreeNode> treeList = new ArrayList<TreeNode>();
			for (AreaInfo item : this.areaInfos) {
				int i = item.getAreaType();
				String parentCode = "";
				switch (i) {
				case 1:
					parentCode = "000000";
					break;
				case 2:
					parentCode = item.getAreaId().substring(0, 2) + "0000";
					break;
				case 3:
					parentCode = item.getAreaId().substring(0, 4) + "00";
					break;
				}
				TreeNode node = new TreeNode(item.getAreaId(), item.getAreaName(), parentCode);
				node.putExtData("areaType", item.getAreaType());
				treeList.add(node);
			}
			tree.setTempNodeList(treeList);
			tree.generateTree();

		}
		TreeNode trees = tree.getRoot() == null ? new TreeNode() : tree.getRoot();
		return trees;

	}

}
