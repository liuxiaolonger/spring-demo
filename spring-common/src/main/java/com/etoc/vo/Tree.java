/**
 * 
 */
package com.etoc.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 树对象，用于构建树
 * @author ETOC-ChenChao
 *
 */
public class Tree {
	/**
	 * 根节点，用于带根形成的树
	 */
	private TreeNode root;
	/**
	 * 分支节点，用于不带根后面形成的树
	 */
	private List<TreeNode> brancheRoots = new ArrayList<>();
	/**
	 * 临时节点集合
	 */
	private List<TreeNode> tempNodeList;
	
	public Tree() {
		super();
		tempNodeList = new ArrayList<>();
	}
	
	public Tree(List<TreeNode> tempNodeList) {
		super();
		this.tempNodeList = tempNodeList;
	}
	
	/**
	 * 生成树结构，现将结点数据都放入集合，最后调用生成树
	 */
	public void generateTree() {
		HashMap<String, TreeNode> nodeMap = putNodesIntoMap();
		putChildIntoParent(nodeMap);
		Collections.sort(brancheRoots, new Comparator<TreeNode>() {

			@Override
			public int compare(TreeNode o1, TreeNode o2) {
				int result = o1.getSort().compareTo(o2.getSort());
				if (result != 0) {
					return result;
				}
				return o1.compareTo(o2);
			}
			
		});// 先按排序码排序，一样则按默认排序规则排序
	}
	
	/**
	 * 将节点list转成节点map
	 * @return
	 */
	private HashMap<String, TreeNode> putNodesIntoMap() {
		//int mark = 0;
		HashMap<String, TreeNode> nodeMap = new HashMap<>();
		Iterator<TreeNode> it = tempNodeList.iterator();
		while (it.hasNext()) {
			TreeNode treeNode = it.next();
			nodeMap.put(treeNode.getId(), treeNode);
		}
		for (TreeNode treeNode : tempNodeList) {
			// 父节点为null，或者父节点查询不到，则设置当前节点为根节点
			if (treeNode.getParentId() == null
					|| nodeMap.get(treeNode.getParentId()) == null) {
				root = treeNode;
				// 集合去重
				if (brancheRoots.contains(treeNode)) {
					brancheRoots.remove(treeNode);
				}
				brancheRoots.add(treeNode);
				// 如果数据集有修改根节点超过两次，则说明无法形成树形，抛出异常
				/*if (++mark > 1) {
					throw new BizException(ResultEnum.DATA_INVALID_THREE);
				}*/
			}
		}
		// 如果是多颗树，则根结点无意义
		if (isMultiTree()) {
			root = null;
		}
		return nodeMap;
	}
	
	/**
	 * 将所有节点放入树形
	 * @param nodeMap
	 */
	private void putChildIntoParent(HashMap<String, TreeNode> nodeMap) {
		Iterator<TreeNode> it = nodeMap.values().iterator();
		while (it.hasNext()) {
			TreeNode treeNode = it.next();
			String parentId = treeNode.getParentId();
			if (nodeMap.containsKey(parentId)) {
				TreeNode parentNode = nodeMap.get(parentId);
				if (parentNode != null) {
					treeNode.setParent(parentNode);
					parentNode.addChildNode(treeNode);
				}
				if (parentNode == null)
                {
                    System.out.println("树形数据异常为1:"+treeNode.getName());
                }
			}else {
			    System.out.println("树形数据异常为2:"+treeNode.toString());
			}
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public List<TreeNode> getTempNodeList() {
		return tempNodeList;
	}

	public void setTempNodeList(List<TreeNode> tempNodeList) {
		this.tempNodeList = tempNodeList;
	}
	
	public List<TreeNode> getBrancheRoots() {
		return brancheRoots;
	}
	
	/**
	 * 判断是否是一棵树
	 * @return
	 */
	public boolean isSingleTree() {
		return brancheRoots.size() == 1;
	}

	/**
	 * 判断是否是多棵树
	 * @return
	 */
	public boolean isMultiTree() {
		return brancheRoots.size() > 1;
	}
	
}
