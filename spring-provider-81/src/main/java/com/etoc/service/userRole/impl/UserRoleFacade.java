package com.etoc.service.userRole.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.base.BaseService;
import com.etoc.base.impl.SysQueryListService;
import com.etoc.model.SysUserRole;
import com.etoc.service.userRole.AbsUserRoleService;
import com.etoc.service.userRole.UserRoleService;
import com.etoc.service.userRole.vo.UserRole;
import com.etoc.util.UUIDUtil;

/**
 * 
 * 用户和角色调度器
 * 
 * @author longlong
 * @version [版本号, 2019年1月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserRoleFacade extends AbsUserRoleService implements UserRoleService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String id;

	private String type;

	// 用户、角色ID集
	private List<String> ids;

	/** {@inheritDoc} */

	@Override
	public UserRoleFacade addQueryId(String type, String id) {
		this.id = id;
		this.type = type;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public UserRoleFacade setObject(String type, String id, List<String> ids) {
		this.id = id;
		this.type = type;
		this.ids = ids;
		return this;
	}

	/** {@inheritDoc} */

	@Override
	public SysQueryListService<UserRole> queryRoleUser() throws Exception {
		QueryListUserRole queryListService = context.getBean(QueryListUserRole.class);
		if (USER_ID.equals(type)) {
			queryListService.addUserId(id);
		} else if (ROLE_ID.equals(type)) {
			queryListService.addRoleId(id);
		} else {
			logger.warn("type参数错误！");
		}

		queryListService.execute();
		return queryListService;
	}

	/** {@inheritDoc} */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService saveRoleUser() throws Exception {
		// 保存关联表的bean
		SaveUserRole sysSaveService = context.getBean(SaveUserRole.class);
		
		List<SysUserRole> lists = new ArrayList<>();// 存放要保存的关联表对象信息
		// 当ids不为空集合时给保存动作传参
		if (CollectionUtils.isNotEmpty(ids)) {
			ids.forEach(URId -> {
				SysUserRole sysUserR = new SysUserRole();
				sysUserR.setId(UUIDUtil.getUUID());
				if (USER_ID.equals(type)) {
					// 根据用户ID保存关联的角色资源
					sysUserR.setRoleId(URId);
					sysUserR.setUserId(id);
				} else if (ROLE_ID.equals(type)) {
					// 根据角色ID保存关联的用户资源
					sysUserR.setUserId(URId);
					sysUserR.setRoleId(id);
				} else {
					logger.warn("保存时type参数错误！");
					return;
				}
				lists.add(sysUserR);
			});
			sysSaveService.addUserRoleList(lists);
		}
		sysSaveService.execute();
		return sysSaveService;
	}

	/** {@inheritDoc} */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveRoleUser() throws Exception {
		RemoveUserRole removeRole = context.getBean(RemoveUserRole.class);// 删除用户和角色关联信息
		SaveUserRole saveRole = context.getBean(SaveUserRole.class); // 保存用户和角色关联信息
		MoveUserRole moveRole = context.getBean(MoveUserRole.class); // 修改关联表bean

		// 当ids为空时不做删除操作
		String id = this.ids == null ? null : this.id;
		if (USER_ID.equals(type)) {
			removeRole.addUserId(id);
		} else if (ROLE_ID.equals(type)) {
			removeRole.addRoleId(id);
		} else {
			logger.warn("删除时type参数错误！");
		}

		List<SysUserRole> lists = new ArrayList<>();// 存放要保存的关联表对象信息
		// 当ids不为空集合时给保存动作传参
		if (CollectionUtils.isNotEmpty(ids)) {
			ids.forEach(URId -> {
				SysUserRole sysUserR = new SysUserRole();
				sysUserR.setId(UUIDUtil.getUUID());
				if (USER_ID.equals(type)) {
					// 根据用户ID保存关联的角色资源
					sysUserR.setRoleId(URId);
					sysUserR.setUserId(id);
				} else if (ROLE_ID.equals(type)) {
					// 根据角色ID保存关联的用户资源
					sysUserR.setUserId(URId);
					sysUserR.setRoleId(id);
				} else {
					logger.warn("保存时type参数错误！");
					return;
				}
				lists.add(sysUserR);
			});
			saveRole.addUserRoleList(lists);
		}
		moveRole.setSysRemoveService(removeRole);
		moveRole.setSysSaveService(saveRole);
		// 执行修改关联表操作
		moveRole.execute();
		return moveRole;
	}


	/** {@inheritDoc} */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService removeRoleUser() throws Exception {
		RemoveUserRole removeUserRole = context.getBean(RemoveUserRole.class);
		if (USER_ID.equals(type)) {
			removeUserRole.addUserId(id);
		} else if (ROLE_ID.equals(type)) {
			removeUserRole.addRoleId(id);
		} else {
			logger.warn("type参数错误！");
		}

		// 执行删除关联表操作
		removeUserRole.execute();
		return removeUserRole;
	}

}
