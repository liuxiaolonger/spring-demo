package com.etoc.service.group.impl;	

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysSaveService;
import com.etoc.constant.StateEnum;
import com.etoc.model.SysUserGroup;
import com.etoc.service.group.AbsUserGroupService;
import com.etoc.service.group.vo.UserGroups;
import com.etoc.service.guser.AbsUGroupUserService;
import com.etoc.service.guser.UGroupUserService;
import com.etoc.util.UUIDUtil;

/**
 * 
 * 保存用户组信息
 * 级联保存用户组关联表
 * 
 * @author  chenzhi
 * @version  [版本号, 2018年12月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveUserGroup extends AbsUserGroupService implements SysSaveService<UserGroups>
{
	private UGroupUserService uGroupUserService;
	
    private UserGroups group;
    
    
    protected SaveUserGroup addObject(UserGroups userGroups)
    {
        this.group = userGroups;
        return this;
    }
    
    protected void setUGroupUserService(UGroupUserService uGroupUserService)
    {
        this.uGroupUserService = uGroupUserService;
    }
    
    
    /**
     * 保存用户组
     */
    @Override
    public void execute() throws Exception
    {
    	group.setUserGroupId(UUIDUtil.getUUID());// 添加用户组主键id
        SysUserGroup userGrou = (SysUserGroup) transferObjectFields(group, SysUserGroup.class, null);
        userGrou.setAvailable(StateEnum.available.name()); // 设置用户组默认状态为激活
        
        userGroupMapper.insertGroup(userGrou);// 添加用户组
        
    	List<String> userIds = group.getUserIds() == null ? null : group.getUserIds(); 
    	// 当ids不为空时，在关联表中添加用户组对应的用户信息
    	uGroupUserService.setObject(AbsUGroupUserService.GROUP_ID, userGrou.getId(), userIds);
    	uGroupUserService.saveUGroupUser();
		
    }
    

    /** {@inheritDoc} */
	 
	@Override
	public UserGroups resultObj() throws Exception {
		return group;
	}
    
}
