package com.etoc.service.roleRes.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysMoveService;
import com.etoc.service.roleRes.AbsRoleResourceService;

/**
 * 
 * 批量修改角色资源关联表 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("moveListRoleResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveListRoleResource extends AbsRoleResourceService implements SysMoveService {
	private RemoveListRoleResource removeService;

	private SaveListRoleResource saveService;

	protected void setSysRemoveService(RemoveListRoleResource removeService) {
		this.removeService = removeService;
	}

	protected void setSysSaveService(SaveListRoleResource saveService) {
		this.saveService = saveService;
	}

	@Override
	public void execute() throws Exception {

		// 1、 根据userId、或者resourceId，删除用户资源关联表数据集
		removeService.execute();

		// 2、根据userIds、或者resourceId，新增关联表记录
		saveService.execute();
	}

}
