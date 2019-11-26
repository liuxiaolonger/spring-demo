package com.etoc.service.guser.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.service.guser.AbsUGroupUserService;

/**
 * 
 * 批量修改用户组关联表
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveListUGUs extends AbsUGroupUserService implements SysMoveService {

	private RemoveListUGUs removeService;

	private SaveListUGUs saveService;

	protected void setSysRemoveService(RemoveListUGUs removeService) {
		this.removeService = removeService;
	}

	protected void setSysSaveService(SaveListUGUs saveService) {
		this.saveService = saveService;
	}

	@Override
	public void execute() throws Exception {

		// 1、 根据userId、或者组Id，删除用户组关联表数据集
		removeService.execute();

		// 2、根据userIds、或者组Id，新增关联表记录
		saveService.execute();
	}

}
