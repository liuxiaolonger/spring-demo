package com.etoc.service.organization.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryTreeService;
import com.etoc.model.SysOrganization;
import com.etoc.service.organization.AbsOrganizationService;
import com.etoc.vo.Tree;
import com.etoc.vo.TreeNode;
import com.github.pagehelper.util.StringUtil;

/**
 * 
 * .组织机构树形查询
 * <功能详细描述>
 * 
 * @author  chuyh
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryTreeOrganization")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryTreeOrganization extends AbsOrganizationService
    implements SysQueryTreeService
{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /*
     * 组织机构状态
     */
    private String available;
    
    /*
     * 查询的结果集合
     */
    List<SysOrganization> sysOrganizations;
    
    protected QueryTreeOrganization setQueryParams(String[] fields, String available)
    {
        if (fields == null)
            super.defaultFiedlds = fields;
        this.available = available;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        logger.info("数据库树形结果查询...");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(available))
        {
            map.put("available", available);
        }
        sysOrganizations = sysOrganizationMapper.selectSysOrganizationsTree(map);
        logger.info("数据库树形查询的结果为: {}", sysOrganizations);
        //List<SysUserGroup> group = sysOrganizationMapper.serlectUserGroup(map);
    }
    
    @Override
    public TreeNode resultObj()
    {
        logger.info("开始组织机构树形转换...");
        if (sysOrganizations == null || sysOrganizations.size() == 0)
            return null;
        
        List<TreeNode> treeList = new ArrayList<TreeNode>();
        Tree tree = new Tree();
        sysOrganizations.forEach(item -> {
            TreeNode node = new TreeNode(item.getCode(), item.getName(), item.getParentCode(), item.getPriority());
            node.putExtData("id", item.getId());
            treeList.add(node);
            
        });
        tree.setTempNodeList(treeList);
        tree.generateTree();
        
        return tree.getRoot() == null ? new TreeNode() : tree.getRoot();
    }
   
    
}
