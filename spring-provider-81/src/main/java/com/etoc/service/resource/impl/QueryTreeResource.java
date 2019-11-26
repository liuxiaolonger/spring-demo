package com.etoc.service.resource.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryTreeService;
import com.etoc.model.SysResource;
import com.etoc.service.resource.AbsResourceService;
import com.etoc.vo.Tree;
import com.etoc.vo.TreeNode;

/**
 * 
 * 获取资源树
 * <功能详细描述>
 * 
 * @author  liuxiaolong
 * @version  [版本号, 2019年1月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("queryTreeResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryTreeResource extends AbsResourceService implements SysQueryTreeService
{
    /*
     * 平台类型
     */
    private Integer systemType;
    
    /*
     * 状态
     */
    private String available;
    
    /*
     * 查询的结果集
     */
    private List<SysResource> sysResources;
    
    protected QueryTreeResource setAvailable(String available)
    {
        
        this.available = available;
        return this;
    }
    
    protected QueryTreeResource setQueryParams(Integer systemType)
    {
        
        this.systemType = systemType;
        return this;
    }
    
    @Override
    public void execute()
        throws Exception
    {
        SysResource sysresource = new SysResource();
        if (systemType != null)
        {
            sysresource.setSystemType(systemType);
        }
        if (StringUtils.isNotEmpty(available))
        {
            sysresource.setAvailable(available);
        }
        sysResources = resourceMapper.selectResourcesByQuery(sysresource);
    }
    
    @Override
    public TreeNode resultObj()
    {
        Tree tree = new Tree();
        if (CollectionUtils.isNotEmpty(sysResources))
        {
            List<TreeNode> treeList = new ArrayList<TreeNode>();
            for (SysResource item : sysResources)
            {
                TreeNode node = new TreeNode(item.getCode(), item.getName(), item.getParentCode(), item.getPriority());
                node.putExtData("id", item.getId());
                treeList.add(node);
            }
            tree.setTempNodeList(treeList);
            tree.generateTree();
        }
        return tree.getRoot() == null ? new TreeNode() : tree.getRoot();
    }
    
}
