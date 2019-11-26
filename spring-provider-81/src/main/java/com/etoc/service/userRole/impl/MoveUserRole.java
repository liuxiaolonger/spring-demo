/*
 * 文 件 名:  MoveUserRole.java
 * 版    权:  ETOC 信息技术有限公司, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  longlong
 * 修改时间:  2019年1月9日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.etoc.service.userRole.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.service.userRole.AbsUserRoleService;

/**
 * 修改用户和资源关联表
 * 
 * @author longlong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveUserRole extends AbsUserRoleService implements SysMoveService {

	private RemoveUserRole removeService;

	private SaveUserRole saveService;

	protected void setSysRemoveService(RemoveUserRole removeService) {
		this.removeService = removeService;
	}

	protected void setSysSaveService(SaveUserRole saveService) {
		this.saveService = saveService;
	}

	/** {@inheritDoc} */

	@Override
	public void execute() throws Exception {
		// 1、 根据userId、或者资源Id，删除用户组关联表数据集
		removeService.execute();

		// 2、根据userIds、或者资源Id，新增关联表记录
		saveService.execute();

	}

}
