/*
 * 文 件 名:  MoveUserPsw.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2019年1月15日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.constant.CommonConst;
import com.etoc.util.MD5Tool;
import com.longer.base.SysMoveService;
import com.longer.dao.model.SysUser;
import com.longer.service.user.AbsUserService;

/**
 * 重置密码
 * 
 * @author longlong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveUserPsw extends AbsUserService implements SysMoveService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userId;

	protected MoveUserPsw addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public void execute() throws Exception {
		// 获取新的盐
		String salt = MD5Tool.getNumRandom(CommonConst.system.STATUS_SIX);
		// 给重置密码加盐
		String newPsw = RESET_PASSWORD;// ShiroUtil.Encryption.MD5(RESET_PASSWORD, salt);
		SysUser sysUser = new SysUser();
		sysUser.setId(userId);
		sysUser.setLoginPsw(MD5Tool.getSaltMD5(newPsw,salt, CommonConst.system.STATUS_TWO));
		sysUser.setSalt(salt);
		logger.info("重置密码的对象信息sysUser  = " + sysUser);
		userMapper.updateByPrimaryKeySelective(sysUser);
		logger.info("重置密码成功！");
	}
	
}
