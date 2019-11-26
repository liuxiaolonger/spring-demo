package com.longer.service.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.constant.CommonConst;
import com.etoc.exception.ChannelException;
import com.etoc.util.MD5Tool;
import com.etoc.util.StringUtil;
import com.longer.base.SysMoveService;
import com.longer.dao.model.SysUser;
import com.longer.service.guser.impl.UGroupUserFacade;
import com.longer.service.user.AbsUserService;
import com.longer.service.user.vo.SysUsers;
import com.longer.service.userRes.impl.UserResourceFacade;
import com.longer.service.userRole.impl.UserRoleFacade;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveUser extends AbsUserService implements SysMoveService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private SysUsers sysUsers;

	private String userId;

	private UserRoleFacade userRoleFacade;

	private UserResourceFacade userResourceFacade;

	private UGroupUserFacade uGroupUserFacade;

	protected MoveUser addUserRoleFacade(UserRoleFacade userRoleFacade) {
		this.userRoleFacade = userRoleFacade;
		return this;
	}

	protected MoveUser addUserResourceFacade(UserResourceFacade userResourceFacade) {
		this.userResourceFacade = userResourceFacade;
		return this;
	}

	protected MoveUser addUGroupUserFacade(UGroupUserFacade uGroupUserFacade) {
		this.uGroupUserFacade = uGroupUserFacade;
		return this;
	}

	protected MoveUser addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	protected MoveUser addUser(SysUsers sysUsers) {
		this.sysUsers = sysUsers;
		return this;
	}

    
	@Override
	public void execute() throws Exception {
		
		SysUser us = userMapper.selectByPrimaryKey(userId);// 通过id查询用户信息
		if(us == null) {
			logger.error("用户主键id有误!");
			throw new ChannelException("用户主键id有误！");
		}
		if(StringUtil.isEmpty(us.getStatus())) {
			logger.error("要修改用户的状态不能为空！");
			throw new ChannelException("要修改用户的状态不能为空！");
		}
		if(!("available".equals(us.getStatus()))) {
			logger.error("只能修改状态为激活的用户！");
			throw new ChannelException("只能修改状态为激活的用户！");
		}
		
		if(StringUtil.isNotEmpty(sysUsers.getLoginPsw())) {
			// 当修改的是密码时，给密码MD5加密
			String salt = MD5Tool.getNumRandom(CommonConst.system.STATUS_SIX);// 获取新的盐
			String newPsw = MD5Tool.getSaltMD5(sysUsers.getLoginPsw(),salt, CommonConst.system.STATUS_TWO);
			sysUsers.setLoginPsw(newPsw);
			sysUsers.setSalt(salt);
		}
		sysUsers.setUserId(userId);
		SysUser user = transferObjectFields(sysUsers, SysUser.class, null);
		logger.info("转化为数据库model对象的结果SysUser = " + user);
		
		
		// 当除主键外其它字段全为空时不执行修改本身操作
		boolean boo = objectIsEmpty(user, MODEL_ID);
		if (!boo) {
			userMapper.updateByPrimaryKeySelective(user);
			logger.info("修改用户对象成功！");
		}
		

		// 当角色ids不为空时，更新关联表中用户对应的角色信息
		userRoleFacade.moveRoleUser();
		// 当资源ids不为空时，更新关联表中用户对应的资源信息
		userResourceFacade.moveUserResource();
		// 当用户组ids不为空时，更新关联表中用户对应的用户组信息
		uGroupUserFacade.moveUGroupUser();

	}

}
