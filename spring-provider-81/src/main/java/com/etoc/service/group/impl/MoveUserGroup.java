package com.etoc.service.group.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.model.SysUserGroup;
import com.etoc.service.group.AbsUserGroupService;
import com.etoc.service.group.vo.UserGroups;
import com.etoc.service.guser.impl.UGroupUserFacade;

/**
 * 
 * 修改用户组信息 级联修改用户组关联表
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveUserGroup extends AbsUserGroupService implements SysMoveService {

	private UserGroups userGroups;

	private String userGroupId;

	private UGroupUserFacade uGroupUserFacade;

	protected void addUGroupUserFacade(UGroupUserFacade uGroupUserFacade) {
		this.uGroupUserFacade = uGroupUserFacade;
	}

	protected void addUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	protected MoveUserGroup addUserGroup(UserGroups userGroups) {
		this.userGroups = userGroups;
		return this;
	}

	/**
	 * 修改用户组
	 */
	@Override
	public void execute() throws Exception {

		userGroups.setUserGroupId(userGroupId);
		// 将resource对象转化为model对象
		SysUserGroup sysUserGroup = transferObjectFields(userGroups, SysUserGroup.class, null);

		boolean boo = objectIsEmpty(sysUserGroup, MODEL_ID);
		// 判断是否是空对象，当非空对象是修改对象本身
		if (!boo) {
			// 修改用户组信息
			userGroupMapper.updateGroup(sysUserGroup);
		}

		// 当用户ids不为空时，更新用户组关联表信息 （内部封装有删除和添加bean）
		uGroupUserFacade.moveUGroupUser();

	}

}
