package com.etoc.service.user.impl;	

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysRemoveService;
import com.etoc.service.guser.impl.UGroupUserFacade;
import com.etoc.service.user.AbsUserService;
import com.etoc.service.userRes.impl.UserResourceFacade;
import com.etoc.service.userRole.impl.UserRoleFacade;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RemoveUser extends AbsUserService implements SysRemoveService {

	private String userId;

	private UserRoleFacade userRoleFacade;

	private UserResourceFacade userResourceFacade;

	private UGroupUserFacade uGroupUserFacade;

	protected RemoveUser addUserRoleFacade(UserRoleFacade userRoleFacade) {
		this.userRoleFacade = userRoleFacade;
		return this;
	}

	protected RemoveUser addUserResourceFacade(UserResourceFacade userResourceFacade) {
		this.userResourceFacade = userResourceFacade;
		return this;
	}

	protected RemoveUser addUGroupUserFacade(UGroupUserFacade uGroupUserFacade) {
		this.uGroupUserFacade = uGroupUserFacade;
		return this;
	}

	protected RemoveUser addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		// 删除用户和角色关联的资源信息
		userRoleFacade.removeRoleUser();
		// 删除用户和资源关联的资源信息
		userResourceFacade.removeUserResource();
		// 删除用户和用户组关联的资源信息
		uGroupUserFacade.removeUGroupUser();
		
		// 删除用户信息
		userMapper.deleteByPrimaryKey(userId);

	}

}
