/**
 * 
 */
package com.etoc.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 树节点对象
 * 增加@JsonIgnoreProperties防止JSON序列化时循环引用
 * 是一个双向关联的树
 * @author ETOC-ChenChao
 *
 */
@JsonIgnoreProperties(value={"parent", "elders", "juniors"}, allowGetters=false)
public class TreeNode implements Serializable, Comparable<TreeNode> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7875135250222974448L;
	/**
	 * 节点唯一标识
	 */
	protected String id;
	/**
	 * 节点名称
	 */
	protected String name;
	/**
	 * 父节点id
	 */
	protected String parentId;
	/**
	 * 父节点
	 */
	protected TreeNode parent;
	/**
	 * 顺序
	 */
	protected Integer sort = 0;// 默认值，防止空指针
	/**
	 * 孩子节点集合
	 */
	protected List<TreeNode> children = new ArrayList<>();
	
	protected Map<String, Object> extDatas = new HashMap<>();
	
	/**
	 * 默认比较器
	 */
	private static TreeNodeComparator comparator = new TreeNodeComparator();
	
	public TreeNode() {
		super();
	}
	
	public TreeNode(String id, String parentId) {
		super();
		this.id = id;
		this.parentId = parentId;
	}

	public TreeNode(String id, String name, String parentId) {
		this(id, parentId);
		this.name = name;
	}
	
	public TreeNode(String id, String name, String parentId, Integer sort) {
		this(id, name, parentId);
		this.sort = sort;
	}

	


	/**
	 * 判断是否是根节点
	 * @return
	 */
	public boolean isRoot() {
		if (parent == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否是叶子节点
	 * @return
	 */
	public boolean isLeaf() {
		if (children.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断节点是否当前节点的孩子节点
	 * @param treeNode
	 * @return
	 */
	public boolean isChildNode(TreeNode treeNode) {
		if (this.equals(treeNode.getParent())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断节点是否当前节点的后辈节点
	 * @param treeNode
	 * @return
	 */
	public boolean isJuniorNode(TreeNode treeNode) {
		TreeNode juniorNode = treeNode;
		while((juniorNode = juniorNode.getParent()) != null) {
			if (this.equals(juniorNode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取父辈节点集合
	 * @return
	 */
	public List<TreeNode> getElders() {
		List<TreeNode> elders = new ArrayList<>();
		if (isRoot()) {
			return elders;
		} else {
			elders.add(parent);
			elders.addAll(parent.getElders());
			return elders;
		}
	}
	
	/**
	 * 获取晚辈节点集合
	 * @return
	 */
	public List<TreeNode> getJuniors() {
		List<TreeNode> juniors = new ArrayList<>();
		if (isLeaf()) {
			return juniors;
		} else {
			int num = children.size();
			for (int i = 0; i < num; i++) {
				TreeNode child = children.get(i);
				juniors.add(child);
				juniors.addAll(child.getJuniors());
			}
			return juniors;
		}
	}
	
	/**
	 * 根据id在树中查询节点
	 * @param id
	 * @return
	 */
	public TreeNode findTreeNodeById(String id) {
		if (this.id.equals(id)) {
			return this;
		}
		if (children.isEmpty()) {
			return null;
		} else {
			int num = children.size();
			for (int i = 0; i < num; i++) {
				TreeNode child = children.get(i);
				TreeNode resultNode = child.findTreeNodeById(id);
				if (resultNode != null) {
					return resultNode;
				}
			}
			return null;
		}
	}
	
	/**
	 * 增加叶子节点，相同的则覆盖
	 * @param treeNode
	 */
	public void addChildNode(TreeNode treeNode) {
		if (isChildNode(treeNode)) {
			if (children.contains(treeNode)) {
				Iterator<TreeNode> it = children.iterator();
				while (it.hasNext()) {
					TreeNode node = it.next();
					if (node.getId().equals(treeNode.getId())) {
						it.remove();
						break;
					}
				}
				/*TreeNode oldNode = findTreeNodeById(treeNode.getId());
				deleteChildNode(oldNode);*/
			}
			children.add(treeNode);
			Collections.sort(children, comparator);
		}
	}
	
	/**
	 * 插入后辈节点
	 * @param treeNode
	 * @return
	 */
	public boolean insertJuniorNode(TreeNode treeNode) {
		if (isChildNode(treeNode)) {
			addChildNode(treeNode);
			return true;
		} else {
			int num = children.size();
			boolean flag = false;
			for (int i = 0; i < num; i++) {
				flag = children.get(i).insertJuniorNode(treeNode);
				if (flag) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * 删除当前节点，如果自己是根节点，则保留
	 */
	public void deleteNode() {
		if (parent != null) {
			parent.deleteChildNode(this);
		}
	}
	
	/**
	 * 删除孩子节点
	 * @param treeNode
	 */
	public void deleteChildNode(TreeNode treeNode) {
		children.remove(treeNode);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Map<String, Object> getExtDatas() {
		return extDatas;
	}

	public TreeNode putExtData(String key, Object value) {
		this.extDatas.put(key, value);
		return this;
	}

	public TreeNode getParent() {
		return parent;
	}
	
	public void setParent(TreeNode parent) {
		this.parent = parent;
		this.parentId = parent.getId();
	}

	public List<TreeNode> getChildren() {
		return children;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(TreeNode o) {
		return id.compareTo(o.getId());
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", name=" + name + ", parentId=" + parentId + ", children="
				+ children + "]";
	}
	
	/**
	 * 比较器
	 * @author ETOC-ChenChao
	 *
	 */
	static class TreeNodeComparator implements Comparator<TreeNode> {

		@Override
		public int compare(TreeNode o1, TreeNode o2) {
			if (o1.equals(o2)) {
				return 0;
			}
			// 如果是同一级节点，则比较顺序号；否则比较在树中的位置
			if (o1.getParent().equals(o2.getParent())) {
				int result = 0;
				// 如果顺序号相同，则使用默认比较规则
				if (o1.getSort() == null || o2.getSort() == null
						|| (result = o1.getSort().compareTo(o2.getSort())) == 0) {
					return o1.compareTo(o2);
				} else {
					return result;
				}
			} else {
				int index = -1;
				// 如果o2是o1的父节点，则返回正数；如果o1是o2的父节点，则返回负数；否则使用默认比较规则
				if ((index = o1.getElders().indexOf(o2)) >= 0) {
					return index+1;
				} else if ((index = o2.getElders().indexOf(o1)) >= 0) {
					return -(index+1);
				} else {
					return o1.compareTo(o2);
				}
			}
			
		}
		
	}

}
