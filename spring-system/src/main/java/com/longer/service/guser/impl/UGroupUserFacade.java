package com.longer.service.guser.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.util.UUIDUtil;
import com.longer.base.BaseService;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysUserGroupUser;
import com.longer.service.guser.AbsUGroupUserService;
import com.longer.service.guser.UGroupUserService;
import com.longer.service.guser.vo.UserGroupUser;

/**
 * 
 * 用户和用户组关联表调度器
 * 
 * @author longlong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UGroupUserFacade extends AbsUGroupUserService implements UGroupUserService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String id;

	private String type;

	// 用户、组ID集
	private List<String> ids;

	@Override
	public UGroupUserFacade addQueryId(String type, String id) {
		this.id = id;
		this.type = type;
		return this;
	}

	@Override
	public UGroupUserFacade setObject(String type, String id, List<String> ids) {
		this.id = id;
		this.type = type;
		this.ids = ids;
		return this;
	}

	/**
	 * 查询关联表信息 {@inheritDoc}
	 */
	public SysQueryListService<UserGroupUser> queryUGroupUser() {
		QueryListUGUs queryListUGUs = context.getBean(QueryListUGUs.class);
		if (USER_ID.equals(type)) {
			queryListUGUs.addGroupId(id);
		} else if (GROUP_ID.equals(type)) {
			queryListUGUs.addGroupId(id);
		} else {
			logger.warn("type参数错误！");
		}

		queryListUGUs.execute();
		return queryListUGUs;
	}

	/*
	 * 保存用户组关联信息
	 * 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService saveUGroupUser() throws Exception {
		// 保存用户组关联用户表
		SaveListUGUs saveUGroup = context.getBean(SaveListUGUs.class);

		List<SysUserGroupUser> lists = new ArrayList<>();// 存放要保存的关联表对象信息
		// 当ids不为空集合时给保存动作传参
		if (CollectionUtils.isNotEmpty(ids)) {
			ids.forEach(UGId -> {
				SysUserGroupUser sysUserGroupInfo = new SysUserGroupUser();
				sysUserGroupInfo.setId(UUIDUtil.getUUID());
				if (USER_ID.equals(type)) {
					// 根据用户ID保存关联的用户组信息
					sysUserGroupInfo.setGroupId(UGId);
					sysUserGroupInfo.setUserId(id);
				} else if (GROUP_ID.equals(type)) {
					// 根据用户组ID保存关联的用户信息
					sysUserGroupInfo.setGroupId(id);
					sysUserGroupInfo.setUserId(UGId);
				} else {
					logger.warn("保存时type参数错误！");
					return;
				}
				lists.add(sysUserGroupInfo);
			});
			saveUGroup.addUserGroupUser(lists);
		}
		saveUGroup.execute();
		return saveUGroup;
	}

	/*
	 * 删除用户组关联信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService removeUGroupUser() throws Exception {
		RemoveListUGUs removeListUGUs = context.getBean(RemoveListUGUs.class);
		if (USER_ID.equals(type)) {
			removeListUGUs.addGroupId(id);
		} else if (GROUP_ID.equals(type)) {
			removeListUGUs.addGroupId(id);
		} else {
			logger.warn("type参数错误！");
		}

		removeListUGUs.execute();
		return removeListUGUs;
	}

	/*
	 * 修改用户组关联信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveUGroupUser() throws Exception {
		RemoveListUGUs removeListUGUs = context.getBean(RemoveListUGUs.class);// 删除用户组关联用户表
		SaveListUGUs saveUGroup = context.getBean(SaveListUGUs.class);// 保存用户组关联用户表
		MoveListUGUs moveListUGUs = context.getBean(MoveListUGUs.class);// 修改关联表bean

		// 当ids为空时不做删除操作
		String id = this.ids == null ? null : this.id;
		if (USER_ID.equals(type)) {
			removeListUGUs.addUserId(id);
		} else if (GROUP_ID.equals(type)) {
			removeListUGUs.addGroupId(id);
		} else {
			logger.warn("删除时type参数错误！");
		}

		List<SysUserGroupUser> lists = new ArrayList<>();// 存放要保存的关联表对象信息
		// 当ids不为空集合时给保存动作传参
		if (CollectionUtils.isNotEmpty(ids)) {
			ids.forEach(UGId -> {
				SysUserGroupUser sysUserGroupInfo = new SysUserGroupUser();
				sysUserGroupInfo.setId(UUIDUtil.getUUID());
				if (USER_ID.equals(type)) {
					// 根据用户ID保存关联的用户组信息
					sysUserGroupInfo.setGroupId(UGId);
					sysUserGroupInfo.setUserId(id);
				} else if (GROUP_ID.equals(type)) {
					// 根据用户组ID保存关联的用户信息
					sysUserGroupInfo.setGroupId(id);
					sysUserGroupInfo.setUserId(UGId);
				} else {
					logger.warn("保存时type参数错误！");
					return;
				}
				lists.add(sysUserGroupInfo);
			});
			saveUGroup.addUserGroupUser(lists);
		}
		moveListUGUs.setSysRemoveService(removeListUGUs);
		moveListUGUs.setSysSaveService(saveUGroup);

		moveListUGUs.execute();
		return moveListUGUs;
	}

}
