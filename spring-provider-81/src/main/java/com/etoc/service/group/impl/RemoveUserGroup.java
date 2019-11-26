package com.etoc.service.group.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysRemoveService;
import com.etoc.service.group.AbsUserGroupService;
import com.etoc.service.guser.impl.UGroupUserFacade;

/**
 * 
 * 删除用户组信息 级联删除用户组关联表
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RemoveUserGroup extends AbsUserGroupService implements SysRemoveService {

	private String groupId;
	
	private UGroupUserFacade uGroupUserFacade;
	
	protected RemoveUserGroup addUGroupUserFacade(UGroupUserFacade uGroupUserFacade) {
		this.uGroupUserFacade = uGroupUserFacade;
		return this;
	}
	
	protected RemoveUserGroup addMoveId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		// 根据用户组id删除关联表中的用户信息
		uGroupUserFacade.removeUGroupUser();
		
		// 删除用户组
		userGroupMapper.deleteGroup(groupId);
	}
}
