package com.etoc.service.user.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysSaveService;
import com.etoc.constant.CommonConst;
import com.etoc.constant.StateEnum;
import com.etoc.model.SysUser;
import com.etoc.service.guser.AbsUGroupUserService;
import com.etoc.service.guser.impl.UGroupUserFacade;
import com.etoc.service.user.AbsUserService;
import com.etoc.service.user.vo.SysUsers;
import com.etoc.service.userRes.AbsUserResourceService;
import com.etoc.service.userRes.impl.UserResourceFacade;
import com.etoc.service.userRole.AbsUserRoleService;
import com.etoc.service.userRole.impl.UserRoleFacade;
import com.etoc.util.MD5Tool;
import com.etoc.util.StringUtil;
import com.etoc.util.UUIDUtil;

import io.netty.channel.ChannelException;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveUser extends AbsUserService implements SysSaveService<SysUsers> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private SysUsers sysUsers;

	private UserRoleFacade userRoleFacade;

	private UserResourceFacade userResourceFacade;

	private UGroupUserFacade uGroupUserFacade;

	protected SaveUser addUserRoleFacade(UserRoleFacade userRoleFacade) {
		this.userRoleFacade = userRoleFacade;
		return this;
	}

	protected SaveUser addUserResourceFacade(UserResourceFacade userResourceFacade) {
		this.userResourceFacade = userResourceFacade;
		return this;
	}

	protected SaveUser addUGroupUserFacade(UGroupUserFacade uGroupUserFacade) {
		this.uGroupUserFacade = uGroupUserFacade;
		return this;
	}

	protected SaveUser addUser(SysUsers sysUsers) {
		this.sysUsers = sysUsers;
		
		return this;
	}
	  
	@Override
	public void execute() throws Exception {
		if(StringUtil.isEmpty(sysUsers.getLoginName())||StringUtil.isEmpty(sysUsers.getLoginPsw())||
				StringUtil.isEmpty(sysUsers.getOrganizationId())) {
			throw new ChannelException("缺少参数！！");
		}
		String salt = MD5Tool.getNumRandom(CommonConst.system.STATUS_SIX);//随机生成6位的盐
		sysUsers.setSalt(salt);
		sysUsers.setLoginPsw(MD5Tool.getSaltMD5(sysUsers.getLoginPsw(), salt, CommonConst.system.STATUS_TWO));//给用户登录密码双层加密
		sysUsers.setUserId(UUIDUtil.getUUID());// 给新增的用户生产随机id
		sysUsers.setCreateTime(new Date());// 创建时间
		SysUser sUser = transferObjectFields(sysUsers, SysUser.class, null);// 把vo对象转化为数据库对象
		sUser.setStatus(StateEnum.available.name());// 设置用户默认为激活状态

		logger.info("添加到数据库的对象user = " + sUser);
		userMapper.insertSelective(sUser);
		logger.info("添加用信息成功。");
		
		List<String> roleIds = sysUsers.getRoleIds() == null ? null : sysUsers.getRoleIds();
		// 当角色ids不为空时，在关联表中添加用户对应的角色信息
		userRoleFacade.setObject(AbsUserRoleService.USER_ID, sysUsers.getUserId(), roleIds);// 给关联表赋值
		userRoleFacade.saveRoleUser();// 执行关联表中新增方法

		List<String> resourceIds = sysUsers.getResourceIds() == null ? null : sysUsers.getResourceIds();
		// 当资源ids不为空时，在关联表中添加用户对应的资源信息
		userResourceFacade.setObject(AbsUserResourceService.USER_ID, sysUsers.getUserId(), resourceIds);// 给关联表赋值
		userResourceFacade.saveUserResource();// 执行关联表中新增方法

		List<String> groupIds = sysUsers.getGroupIds() == null ? null : sysUsers.getGroupIds();
		// 当用户组ids不为空时，在关联表中添加用户对应的用户组信息
		uGroupUserFacade.setObject(AbsUGroupUserService.USER_ID, sysUsers.getUserId(), groupIds);// 给关联表赋值
		uGroupUserFacade.saveUGroupUser();// 执行关联表中新增方法
	}

	@Override
	public SysUsers resultObj() throws Exception {
		// 把密码设置为空
		sysUsers.setLoginPsw(null);
		return sysUsers;
	}


}
