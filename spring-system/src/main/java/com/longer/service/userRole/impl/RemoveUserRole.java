/*
 * 文 件 名:  RemoveUserRole.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2019年1月9日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.longer.service.userRole.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.SysRemoveService;
import com.longer.service.userRole.AbsUserRoleService;

/**
 * 删除用户和资源关联表
 * 
 * @author longlong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RemoveUserRole extends AbsUserRoleService implements SysRemoveService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userId;

	private String roleId;

	protected RemoveUserRole addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	protected RemoveUserRole addRoleId(String roleId) {
		this.roleId = roleId;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)) {
			// 根据用户ID删除关联表数据
			map.put(MODEL_USERID, userId);
		}
		if (StringUtils.isNotEmpty(roleId)) {
			// 根据角色ID删除关联表数据
			map.put(MODEL_ROLEID, roleId);
		}

		logger.info("执行删除关联表的参数userId= {}, roleId= {}", userId, roleId);

		// 当参数不为空时执行sql语句
		if (map.size() != 0) {
			sysUserRoleMapper.deleteByPrimaryKey(map);
		}

	}

}
